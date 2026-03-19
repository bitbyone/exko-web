package io.exko.scopedcss.spring

import io.exko.scopedcss.Styled
import org.hotswap.agent.javassist.CtClass
import org.hotswap.agent.javassist.CtMethod
import org.hotswap.agent.javassist.Modifier
import org.hotswap.agent.javassist.NotFoundException
import org.hotswap.agent.annotation.Init
import org.hotswap.agent.annotation.LoadEvent
import org.hotswap.agent.annotation.OnClassLoadEvent
import org.hotswap.agent.annotation.Plugin
import org.hotswap.agent.command.Command
import org.hotswap.agent.command.Scheduler
import org.hotswap.agent.util.PluginManagerInvoker

@Plugin(
    name = "ScopedCssPlugin",
    description = "Transforms Styled objects into POJOs with linear refresh logic, supports hot-reload via DCEVM",
    testedVersions = [""],
)
class ScopedCssPlugin {

    @Init
    lateinit var scheduler: Scheduler

    companion object {

        @JvmStatic
        @OnClassLoadEvent(classNameRegexp = "io\\.exko\\.scopedcss\\.Styled", events = [LoadEvent.DEFINE])
        fun onStyledInit(ctClass: CtClass) {
            ctClass.declaredConstructors.forEach { c ->
                c.insertAfter(PluginManagerInvoker.buildInitializePlugin(ScopedCssPlugin::class.java))
            }
            println("[ScopedCssPlugin] Injected plugin init into Styled constructor")
        }

        @JvmStatic
        @OnClassLoadEvent(classNameRegexp = ".*", events = [LoadEvent.DEFINE])
        fun onDefine(ctClass: CtClass) {
            if (!isStyled(ctClass)) return
            transform(ctClass)
        }

        fun isStyled(ctClass: CtClass): Boolean {
            return ctClass.superclassName == "io.exko.scopedcss.Styled"
        }

        fun transform(ctClass: CtClass) {
            try {
                if (ctClass.isFrozen) ctClass.defrost()
                ctClass.stopPruning(true)

                println("[ScopedCssPlugin] TRANSFORMING: ${ctClass.name}")

                val cssProperties = mutableMapOf<String, String>()
                for (method in ctClass.declaredMethods) {
                    if (method.name.contains("\$lambda")) {
                        val propName = method.name.substringBefore("_delegate").substringBefore("\$lambda")
                        if (propName.isNotEmpty() && propName != "null") {
                            cssProperties[propName] = method.name
                            method.modifiers = Modifier.PUBLIC
                            method.methodInfo.codeAttribute?.let { if (it.maxLocals < 1) it.maxLocals = 1 }
                        }
                    }
                }

                for (method in ctClass.declaredMethods) {
                    if (method.name.startsWith("get") && method.parameterTypes.isEmpty()) {
                        val rawName = method.name.substring(3)
                        val propName = rawName.substring(0, 1).lowercase() + rawName.substring(1)
                        if (cssProperties.containsKey(propName)) {
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
                for ((propName, lambdaName) in cssProperties) {
                    refreshBody.append("this.registerCss(\"$propName\", this.$lambdaName()); ")
                }
                refreshBody.append("}")

                try {
                    ctClass.getDeclaredMethod("\$refreshStyles").setBody(refreshBody.toString())
                } catch (e: NotFoundException) {
                    ctClass.addMethod(CtMethod.make("public void \$refreshStyles() $refreshBody", ctClass))
                }

                for (c in ctClass.constructors) {
                    c.modifiers = Modifier.PUBLIC
                    if (c.parameterTypes.isEmpty()) {
                        c.setBody("{ super(); }")
                    }
                }

                val clinitBody = "{ INSTANCE = new " + ctClass.name + "(); INSTANCE.\$refreshStyles(); }"
                val clinit = ctClass.classInitializer ?: ctClass.makeClassInitializer()
                clinit.setBody(clinitBody)

                println("[ScopedCssPlugin] SUCCESS: ${ctClass.name}")
            } catch (e: Exception) {
                println("[ScopedCssPlugin] ERROR: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    @OnClassLoadEvent(classNameRegexp = ".*", events = [LoadEvent.REDEFINE])
    fun onRedefine(ctClass: CtClass, classLoader: ClassLoader) {
        if (!isStyled(ctClass)) return
        println("[ScopedCssPlugin] REDEFINE transform: ${ctClass.name}")
        transform(ctClass)

        val className = ctClass.name
        scheduler.scheduleCommand(object : Command {
            override fun executeCommand() {
                try {
                    val clazz = classLoader.loadClass(className)
                    val instance = clazz.getField("INSTANCE").get(null)
                    clazz.getMethod("\$refreshStyles").invoke(instance)
                    println("[ScopedCssPlugin] REFRESHED: $className")
                } catch (e: Exception) {
                    println("[ScopedCssPlugin] REFRESH ERROR: ${e.message}")
                    e.printStackTrace()
                }
            }
        }, 100)
    }
}
