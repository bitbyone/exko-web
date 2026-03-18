package io.exko.scopedcss

import io.exko.html.classes
import kotlinx.html.Tag
import org.intellij.lang.annotations.Language
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AssignableTypeFilter
import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

data class Rules(
    val rules: String
)

abstract class Styled {

    init {
        StyledBundle.register(this)
    }

    private val hash: String = this::class.qualifiedName
        ?.hashCode()?.toUInt()?.toString(16)?.takeLast(6)?.padStart(6, '0')
        ?: error("ScopedStyleSheet must have a class name")

    val declarations = ConcurrentHashMap<String, CssDeclaration>()

    fun renderCss(): String = declarations.values.joinToString("\n\n") { decl ->
        ".${decl.className} {\n${decl.rules().trimIndent().replaceIndent("  ")}\n}"
    }

    fun reload() {
        for (property in this::class.members) {
            (property as? KProperty)?.let {
                property.getter.call(this)
            }
        }
    }

    inner class Css(
        @param:Language("css", prefix = ".x{", suffix = "}") val rules: () -> String
    ) : ReadOnlyProperty<Any?, String> {

        init {

        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            println("lambda: " + rules)
            val delegate = thisRef!!::class.java.getDeclaredField(property.name + $$"$delegate")
            delegate.trySetAccessible()
            val lambdaDel = delegate.get(thisRef!!) as Css
            println("lambda delegate: " + lambdaDel.rules)
            println("Reading property '${property.name}'")
            println(rules())
            val name = "${property.name}-$hash"
            (thisRef as Styled).declarations[property.name] = CssDeclaration(name, rules)
            return name
        }
    }
}

data class CssDeclaration(val className: String, val rules: () -> String)

object StyledBundle {

    private val _instances = mutableSetOf<Styled>()
    val instances: Set<Styled>
        get() = _instances

    fun register(styled: Styled) {
        _instances.add(styled)
    }
}

fun findAndInitStyledObjects(basePackage: String) {
    val scanner = ClassPathScanningCandidateComponentProvider(false)
    scanner.addIncludeFilter(AssignableTypeFilter(Styled::class.java))
    val candidates = scanner.findCandidateComponents(basePackage)
    candidates.forEach { beanDefinition ->
        val className = beanDefinition.beanClassName
        try {
            Class.forName(className)
        } catch (e: Exception) {
            System.err.println("Failed to initialize $className: ${e.message}")
        }
    }
    StyledBundle.instances.forEach {
        println(it.renderCss())
    }
}
