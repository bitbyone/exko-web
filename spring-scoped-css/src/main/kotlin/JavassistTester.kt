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

    val cssProps = mutableMapOf<String, String>()
    for (m in ctClass.declaredMethods) {
        if (m.name.contains("\$lambda")) {
            val pName = m.name.substringBefore("_delegate").substringBefore("\$lambda")
            if (pName.isNotEmpty()) {
                cssProps[pName] = m.name
                m.modifiers = Modifier.PUBLIC
                m.methodInfo.codeAttribute?.let { it.maxLocals = 1 }
            }
        }
    }

    for (method in ctClass.declaredMethods) {
        if (method.name.startsWith("get") && method.parameterTypes.isEmpty()) {
            val rawName = method.name.substring(3)
            val propName = rawName.substring(0, 1).lowercase() + rawName.substring(1)
            if (cssProps.containsKey(propName)) {
                method.setBody("{ return \"" + propName + "-\" + this.getHashStr(); }")
            }
        }
    }

    for (field in ctClass.declaredFields) {
        if (field.name.contains("\$delegate") || field.name == "\$\$delegatedProperties") {
            ctClass.removeField(field)
        } else {
            field.modifiers = field.modifiers and Modifier.FINAL.inv()
        }
    }
    
    val refreshBody = StringBuilder("{ this.getDeclarations().clear(); ")
    for ((propName, lambdaName) in cssProps) {
        refreshBody.append("this.registerCss(\"$propName\", this.$lambdaName()); ")
    }
    refreshBody.append("}")
    
    try {
        ctClass.getDeclaredMethod("\$refreshStyles").setBody(refreshBody.toString())
    } catch (e: Exception) {
        ctClass.addMethod(CtMethod.make("public void \$refreshStyles() $refreshBody", ctClass))
    }
    
    // PURE SEQUENCE
    val clinitBody = "{ INSTANCE = new ${ctClass.name}(); INSTANCE.\$refreshStyles(); }"
    (ctClass.classInitializer ?: ctClass.makeClassInitializer()).setBody(clinitBody)
    
    for (c in ctClass.constructors) {
        c.modifiers = Modifier.PUBLIC
        if (c.parameterTypes.isEmpty()) {
            c.setBody("{ super(); }")
        }
    }
    
    ctClass.writeFile("test-output")
    println("Done. Check test-output with javap. clinit logic is now a simple sequence.")
}
