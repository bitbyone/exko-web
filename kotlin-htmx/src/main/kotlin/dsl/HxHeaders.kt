package io.exko.htmx.dsl

import org.intellij.lang.annotations.Language

// hx-headers — add/override HTTP request headers
// Format: JSON object, or "js:{...}" for dynamic values
class HxHeaders : HxAttribute {
    override val name: String = "hx-headers"
    private var value: String = "{}"

    override fun content(): String = value

    fun json(@Language("JSON") json: String): HxHeaders { value = json; return this }
    fun js(@Language("JavaScript") expression: String): HxHeaders { value = "js:$expression"; return this }
}

class HxHeadersBuilder {
    private val entries = mutableListOf<String>()

    fun header(name: String, value: String) { entries.add("\"$name\": \"${value.replace("\"", "\\\"")}\"") }

    fun build(): String = entries.joinToString(", ", "{", "}")
}

fun HxAttributes.headers(@Language("JSON") json: String) {
    add(HxHeaders().json(json))
}

fun HxAttributes.headers(configure: HxHeadersBuilder.() -> Unit) {
    val builder = HxHeadersBuilder()
    builder.configure()
    add(HxHeaders().json(builder.build()))
}

fun HxAttributes.headersJs(@Language("JavaScript") expression: String) {
    add(HxHeaders().js(expression))
}
