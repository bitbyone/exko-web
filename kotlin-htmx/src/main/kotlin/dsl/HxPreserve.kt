package io.exko.htmx.dsl

// hx-preserve — keep element unchanged across swaps (e.g. a playing <video>)
// The element must have a stable id attribute.
class HxPreserve : HxAttribute {
    override val name: String = "hx-preserve"
    override fun content(): String = "true"
}

fun HxAttributes.preserve() {
    add(HxPreserve())
}
