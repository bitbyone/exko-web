package io.exko.htmx.dsl

import io.exko.html.*
import kotlinx.html.Tag

interface HxAttribute {
    val name: String
    fun content(): String
}

class HxAttributes {

    private val attrs = mutableListOf<HxAttribute>()

    fun apply(tag: Tag) {
        attrs.forEach {
            tag.attributes[it.name] = it.content()
        }
    }

    fun add(attr: HxAttribute) {
        attrs.add(attr)
    }
}

fun Component.hx(
    attributes: HxAttributes.() -> Unit
) {
    val attrs = HxAttributes()
    attrs.attributes()
    attrs.apply(this)
}
