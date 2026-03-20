package io.exko.scopedcss.spring

import org.hotswap.agent.javassist.*
import java.io.File

fun main() {
    val pool = ClassPool.getDefault()
    pool.insertClassPath(File("../../../common/ui/build/classes/kotlin/main").absolutePath)
    pool.insertClassPath(File("../../../lib/exko-web/kotlin-scoped-css/build/classes/kotlin/main").absolutePath)

    val ctClass = pool.get("one.bitby.monnage.common.ui.component.FormDialogStyles")
    if (ctClass.isFrozen) ctClass.defrost()
    ctClass.stopPruning(true)

    // ---- BEFORE: scan <clinit> for all PUTSTATIC instructions ----
    println("=== BEFORE TRANSFORM ===")
    println("Fields:")
    for (f in ctClass.declaredFields) {
        println("  ${f.name} : ${f.type.name} (modifiers=${Modifier.toString(f.modifiers)})")
    }

    val clinit = ctClass.classInitializer
    if (clinit != null) {
        println("\n<clinit> PUTSTATIC instructions:")
        val codeAttr = clinit.methodInfo.codeAttribute
        val constPool = clinit.methodInfo.constPool
        if (codeAttr != null) {
            val iter = codeAttr.iterator()
            var prevOp = -1
            var prevPos = -1
            while (iter.hasNext()) {
                val pos = iter.next()
                val op = iter.byteAt(pos) and 0xFF
                if (op == 0xB3) { // PUTSTATIC
                    val fieldRef = iter.u16bitAt(pos + 1)
                    val fieldName = constPool.getFieldrefName(fieldRef)
                    val fieldType = constPool.getFieldrefType(fieldRef)
                    val value = extractConstant(prevOp, prevPos, iter, constPool)
                    println("  PUTSTATIC $fieldName ($fieldType) <- prevOp=0x${prevOp.toString(16)} value=$value")
                }
                prevOp = op
                prevPos = pos
            }
        }
    }

    // ---- TRANSFORM (same as ScopedCssPlugin) ----
    val cssProperties = mutableMapOf<String, String>()
    for (m in ctClass.declaredMethods) {
        if (m.name.contains("\$lambda")) {
            val pName = m.name.substringBefore("_delegate").substringBefore("\$lambda")
            if (pName.isNotEmpty() && pName != "null") {
                cssProperties[pName] = m.name
                m.modifiers = Modifier.PUBLIC
                m.methodInfo.codeAttribute?.let { if (it.maxLocals < 1) it.maxLocals = 1 }
            }
        }
    }
    println("\nCSS properties: $cssProperties")

    for (method in ctClass.declaredMethods) {
        if (method.name.startsWith("get") && method.parameterTypes.isEmpty()) {
            val rawName = method.name.substring(3)
            val propName = rawName.substring(0, 1).lowercase() + rawName.substring(1)
            if (cssProperties.containsKey(propName)) {
                method.setBody("{ return \"" + propName + "-\" + this.getHashStr(); }")
            }
        }
    }

    // Extract non-delegate field inits from <clinit> BEFORE removing fields
    val fieldInits = extractFieldInitsFromClinit(ctClass, cssProperties)
    println("Extracted field inits: $fieldInits")

    for (field in ctClass.declaredFields) {
        if (field.name.contains("\$delegate") || field.name == "\$\$delegatedProperties") {
            ctClass.removeField(field)
        } else {
            field.modifiers = field.modifiers and Modifier.FINAL.inv()
        }
    }

    val refreshBody = StringBuilder("{ this.getDeclarations().clear(); ")
    for ((propName, lambdaName) in cssProperties) {
        refreshBody.append("this.registerCss(\"$propName\", this.$lambdaName()); ")
    }
    refreshBody.append("}")

    try {
        ctClass.getDeclaredMethod("\$refreshStyles").setBody(refreshBody.toString())
    } catch (e: Exception) {
        ctClass.addMethod(CtMethod.make("public void \$refreshStyles() $refreshBody", ctClass))
    }

    for (c in ctClass.constructors) {
        c.modifiers = Modifier.PUBLIC
        if (c.parameterTypes.isEmpty()) {
            c.setBody("{ super(); }")
        }
    }

    // Build <clinit> with preserved field inits
    val clinitBody = StringBuilder("{ ")
    for ((name, value) in fieldInits) {
        clinitBody.append("$name = $value; ")
    }
    clinitBody.append("INSTANCE = new ${ctClass.name}(); INSTANCE.\$refreshStyles(); }")
    println("New <clinit> body: $clinitBody")

    val newClinit = ctClass.classInitializer ?: ctClass.makeClassInitializer()
    newClinit.setBody(clinitBody.toString())

    ctClass.writeFile("test-output")
    println("\n=== AFTER TRANSFORM ===")
    println("Written to test-output. Verify with javap.")
}

fun extractFieldInitsFromClinit(
    ctClass: CtClass,
    cssProperties: Map<String, String>,
): Map<String, String> {
    val inits = mutableMapOf<String, String>()
    val delegateFieldNames = ctClass.declaredFields
        .filter { it.name.contains("\$delegate") || it.name == "\$\$delegatedProperties" }
        .map { it.name }
        .toSet()

    val clinit = ctClass.classInitializer ?: return inits
    val codeAttr = clinit.methodInfo.codeAttribute ?: return inits
    val constPool = clinit.methodInfo.constPool
    val iter = codeAttr.iterator()

    var prevOp = -1
    var prevPos = -1

    while (iter.hasNext()) {
        val pos = iter.next()
        val op = iter.byteAt(pos) and 0xFF

        if (op == 0xB3) { // PUTSTATIC
            val fieldRef = iter.u16bitAt(pos + 1)
            val fieldName = constPool.getFieldrefName(fieldRef)

            if (fieldName !in delegateFieldNames
                && fieldName != "INSTANCE"
                && fieldName != "\$\$delegatedProperties"
                && fieldName !in cssProperties
                && fieldName !in inits
            ) {
                extractConstant(prevOp, prevPos, iter, constPool)?.let {
                    inits[fieldName] = it
                }
            }
        }

        prevOp = op
        prevPos = pos
    }

    return inits
}

fun extractConstant(
    op: Int,
    pos: Int,
    iter: org.hotswap.agent.javassist.bytecode.CodeIterator,
    constPool: org.hotswap.agent.javassist.bytecode.ConstPool,
): String? {
    return when (op) {
        0x12 -> { // LDC
            val idx = iter.byteAt(pos + 1) and 0xFF
            when (constPool.getTag(idx)) {
                8 -> { // CONSTANT_String
                    val str = constPool.getStringInfo(idx)
                    "\"${str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r")}\""
                }
                3 -> constPool.getIntegerInfo(idx).toString()
                4 -> "${constPool.getFloatInfo(idx)}f"
                else -> null
            }
        }
        0x13 -> { // LDC_W
            val idx = iter.u16bitAt(pos + 1)
            when (constPool.getTag(idx)) {
                8 -> {
                    val str = constPool.getStringInfo(idx)
                    "\"${str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r")}\""
                }
                3 -> constPool.getIntegerInfo(idx).toString()
                4 -> "${constPool.getFloatInfo(idx)}f"
                else -> null
            }
        }
        0x14 -> { // LDC2_W
            val idx = iter.u16bitAt(pos + 1)
            when (constPool.getTag(idx)) {
                5 -> "${constPool.getLongInfo(idx)}L"
                6 -> constPool.getDoubleInfo(idx).toString()
                else -> null
            }
        }
        0x02 -> "-1"      // ICONST_M1
        0x03 -> "0"       // ICONST_0
        0x04 -> "1"       // ICONST_1
        0x05 -> "2"       // ICONST_2
        0x06 -> "3"       // ICONST_3
        0x07 -> "4"       // ICONST_4
        0x08 -> "5"       // ICONST_5
        0x10 -> (iter.byteAt(pos + 1).toByte()).toString() // BIPUSH
        0x11 -> iter.s16bitAt(pos + 1).toString()          // SIPUSH
        0x01 -> "null"    // ACONST_NULL
        else -> null
    }
}
