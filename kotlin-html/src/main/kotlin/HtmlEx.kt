package io.exko.html

import kotlinx.html.*
import org.intellij.lang.annotations.Language

typealias Component = FlowContent
typealias Children = FlowContent.() -> Unit
typealias TypedChildren<T> = T.() -> Unit

/**
 * A marker annotation for a UI functions.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class UI

/**
 * A marker annotation for a Html tag classes.
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class HtmlTag

fun <T : Tag> T.maybe(children: TypedChildren<T>?) {
    children?.invoke(this)
}

// fun HTMLTag.classes(
//    @Language("html", prefix = "<i class='", suffix = "' />")
//    classes: String
// ) {
//    val existing = attributes["class"]
//    attributes["class"] = existing?.let { "$it $classes" } ?: classes
// }

fun FlowContent.classes(
    @Language("html", prefix = "<i class='", suffix = "' />")
    classes: String,
) {
    val existing = attributes["class"]
    attributes["class"] = existing?.let { "$it $classes" } ?: classes
}

fun classes(
    @Language("html", prefix = "<i class='", suffix = "' />")
    vararg cls: String,
): String {
    return cls.joinToString(" ") {
        it.split("\\s+".toRegex())
            .joinToString(" ")
    }.trim()
}

data class Raw<T>(val parent: T)

val SCRIPT.raw: Raw<SCRIPT>
    get() = Raw(this)

val STYLE.raw: Raw<STYLE>
    get() = Raw(this)

infix fun Raw<SCRIPT>.json(@Language("JSON") json: String) = parent.unsafe { +json.trimIndent() }
infix fun Raw<SCRIPT>.js(@Language("javascript") js: String) = parent.unsafe { +js.trimIndent() }
infix fun Raw<STYLE>.css(@Language("CSS") css: String) = parent.unsafe { +css.trimIndent() }

fun FlowContent.js(@Language("javascript") js: String) {
    script {
        type = "module"
        unsafe { +("\n" + js.trimIndent() + "\n") }
    }
}
