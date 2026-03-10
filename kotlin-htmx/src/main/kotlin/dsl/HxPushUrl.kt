package io.exko.htmx.dsl

// hx-push-url — push the request URL into the browser history stack
// Values: "true" | "false" | "<url>"
class HxPushUrl : HxAttribute {
    override val name: String = "hx-push-url"
    private var value: String = "true"

    override fun content(): String = value

    fun enabled(enabled: Boolean = true): HxPushUrl { value = "$enabled"; return this }
    fun url(url: String): HxPushUrl { value = url; return this }
}

fun HxAttributes.pushUrl(enabled: Boolean = true) {
    add(HxPushUrl().enabled(enabled))
}

fun HxAttributes.pushUrl(url: String) {
    add(HxPushUrl().url(url))
}
