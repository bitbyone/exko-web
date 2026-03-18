package io.exko.scopedcss.spring

import org.hotswap.agent.annotation.OnClassLoadEvent
import org.hotswap.agent.annotation.Plugin

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
            }
        }
    }
}
