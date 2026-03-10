package io.exko.htmx.dsl

// hx-replace-url — replace the current URL in the browser location bar (no new history entry)
// Values: "true" | "false" | "<url>"
class HxReplaceUrl : HxAttribute {
    override val name: String = "hx-replace-url"
    private var value: String = "true"

    override fun content(): String = value

    fun enabled(enabled: Boolean = true): HxReplaceUrl { value = "$enabled"; return this }
    fun url(url: String): HxReplaceUrl { value = url; return this }
}

fun HxAttributes.replaceUrl(enabled: Boolean = true) {
    add(HxReplaceUrl().enabled(enabled))
}

fun HxAttributes.replaceUrl(url: String) {
    add(HxReplaceUrl().url(url))
}
