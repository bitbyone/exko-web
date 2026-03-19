package io.exko.scopedcss

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AssignableTypeFilter
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class Styled {

    init {
        StyledBundler.register(this)
    }

    private val hash: String = this::class.qualifiedName
        ?.hashCode()?.toUInt()?.toString(16)?.takeLast(6)?.padStart(6, '0')
        ?: error("ScopedStyleSheet must have a class name")
        
    fun getHashStr(): String = hash

    val declarations = ConcurrentHashMap<String, CssDeclaration>()

    fun registerCss(propertyName: String, css: String) {
        val className = "$propertyName-${getHashStr()}"
        declarations[propertyName] = CssDeclaration(className) { css }
    }

    fun renderCss(): String = declarations.values.joinToString("\n\n") { decl ->
        ".${decl.className} {\n${decl.rules().trimIndent().replaceIndent("  ")}\n}"
    }

    protected fun Css(rules: () -> String) = CssDelegateProvider(rules)

    inner class CssDelegateProvider(val rules: () -> String) {

        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyProperty<Any?, String> {
            declarations[property.name] = CssDeclaration(property.className(), rules)
            return CssDelegate()
        }
    }

    inner class CssDelegate : ReadOnlyProperty<Any?, String> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return property.className()
        }
    }

    private fun KProperty<*>.className() = "${this.name}-$hash"
}

data class CssDeclaration(val className: String, val rules: () -> String)

object StyledBundler {

    private val _instances = mutableSetOf<Styled>()
    val instances: Set<Styled>
        get() = _instances

    fun register(styled: Styled) {
        _instances.add(styled)
    }

    fun bundle(): CssBundle {
        val content = instances
            .joinToString("\n\n") { it.renderCss() }
        val hash = sha256(content).take(8)
        val bundle = CssBundle(
            content = content,
            hash = hash,
            path = "/__scoped-css/styles-$hash.css",
        )
        return bundle
    }
    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
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
}
