package io.exko.htmx.dsl

import org.intellij.lang.annotations.Language

// hx-vals — add extra parameters to the AJAX request
// Format: JSON object literal, or "js:{...}" / "javascript:{...}" for dynamic values
class HxVals : HxAttribute {
    override val name: String = "hx-vals"
    private var value: String = "{}"

    override fun content(): String = value

    fun json(@Language("JSON") json: String): HxVals { value = json; return this }
    fun js(@Language("JavaScript") expression: String): HxVals { value = "js:$expression"; return this }
    fun javascript(@Language("JavaScript") expression: String): HxVals { value = "javascript:$expression"; return this }
}

class HxValsBuilder {
    private val entries = mutableListOf<String>()

    fun field(name: String, value: String) { entries.add("\"$name\": \"${value.replace("\"", "\\\"")}\"") }
    fun field(name: String, value: Number) { entries.add("\"$name\": $value") }
    fun field(name: String, value: Boolean) { entries.add("\"$name\": $value") }
    fun fieldRaw(name: String, jsonValue: String) { entries.add("\"$name\": $jsonValue") }

    fun build(): String = entries.joinToString(", ", "{", "}")
}

fun HxAttributes.vals(@Language("JSON") json: String) {
    add(HxVals().json(json))
}

fun HxAttributes.vals(configure: HxValsBuilder.() -> Unit) {
    val builder = HxValsBuilder()
    builder.configure()
    add(HxVals().json(builder.build()))
}

fun HxAttributes.valsJs(@Language("JavaScript") expression: String) {
    add(HxVals().js(expression))
}
