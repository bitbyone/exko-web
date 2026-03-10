package io.exko.htmx.dsl

// hx-confirm — show a browser confirm() dialog before issuing a request
class HxConfirm(private val message: String) : HxAttribute {
    override val name: String = "hx-confirm"
    override fun content(): String = message
}

fun HxAttributes.confirm(message: String) {
    add(HxConfirm(message))
}
