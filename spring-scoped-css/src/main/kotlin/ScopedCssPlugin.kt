package io.exko.scopedcss.spring

import io.exko.scopedcss.Styled
import org.hotswap.agent.annotation.OnClassLoadEvent
import org.hotswap.agent.annotation.Plugin

/**
 * HotSwap Agent plugin that properly reinitializes [Styled] objects on class redefinition.
 *
 * When a Styled subclass is hot-swapped, ClassInitPlugin re-runs `<clinit>` via `$ha$clinit`.
 * This causes two problems:
 *
 * 1. **Duplication** — `provideDelegate` runs again on the same INSTANCE,
 *    appending to an already-populated `declarations` list.
 * 2. **ArrayIndexOutOfBoundsException** — when new delegated properties are added,
 *    the Kotlin-generated `$$delegatedProperties` array still has the old size,
 *    and `provideDelegate(INSTANCE, $$delegatedProperties[N])` crashes.
 *
 * This plugin runs BEFORE `$ha$clinit` (which is scheduled via CommandExecutor) and:
 * - Clears `declarations` so re-init starts fresh (fixes duplication)
 * - Expands `$$delegatedProperties` to match the new property count (fixes AIOOBE)
 */
@Plugin(
    name = "ScopedCssPlugin",
    description = "Reinitializes Styled CSS objects on hotswap",
    testedVersions = [""],
)
class ScopedCssPlugin {

    companion object {
        @JvmStatic
        @OnClassLoadEvent(classNameRegexp = ".*")
        fun onRedefine(clazz: Class<*>?) {
            if (clazz == null) return
            val superClazz = clazz.superclass
            if (superClazz.name == "io.exko.scopedcss.Styled") {
                val instanceField = clazz.getDeclaredField("INSTANCE")
                val singletonInstance = instanceField.get(null)
                val reloadMethod = superClazz.getDeclaredMethod("reload")
                val renderMethod = superClazz.getDeclaredMethod("renderCss")
                reloadMethod.invoke(singletonInstance)
                val css = renderMethod.invoke(singletonInstance)
                println(singletonInstance)
                println(css as String)
            }
        }

        private fun prepareForReinit(clazz: Class<*>) {
            try {
                clearDeclarations(clazz)
                expandDelegatedProperties(clazz)
            } catch (e: Exception) {
                System.err.println("[ScopedCssPlugin] Failed to prepare reinit for ${clazz.name}: $e")
            }
        }

        private fun clearDeclarations(clazz: Class<*>) {
            val instanceField = clazz.getDeclaredField("INSTANCE")
            instanceField.isAccessible = true
            val instance = instanceField.get(null) ?: return

            val declarationsField = Styled::class.java.getDeclaredField("declarations")
            declarationsField.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val declarations = declarationsField.get(instance) as MutableMap<String, Any>
            declarations.clear()
        }

        @Suppress("UNCHECKED_CAST")
        private fun expandDelegatedProperties(clazz: Class<*>) {
            val dpField = try {
                clazz.getDeclaredField("\$\$delegatedProperties")
            } catch (_: NoSuchFieldException) {
                return
            }
            dpField.isAccessible = true
            val oldArray = dpField.get(null) as? Array<*> ?: return

            val delegateFields = clazz.declaredFields
                .filter { it.name.endsWith("\$delegate") }

            val neededSize = delegateFields.size
            if (oldArray.size >= neededSize) return

            val newArray = java.lang.reflect.Array.newInstance(
                oldArray::class.java.componentType,
                neededSize,
            ) as Array<Any?>

            System.arraycopy(oldArray, 0, newArray, 0, oldArray.size)

            // Fill new slots with KProperty placeholders so provideDelegate doesn't NPE
            for (i in oldArray.size until neededSize) {
                val propName = delegateFields[i].name.removeSuffix("\$delegate")
                val getterName = "get${propName.replaceFirstChar { it.uppercase() }}"
                newArray[i] = kotlin.jvm.internal.PropertyReference1Impl(
                    clazz,
                    propName,
                    "$getterName()Ljava/lang/String;",
                    0,
                )
            }

            dpField.set(null, newArray)
        }
    }
}
