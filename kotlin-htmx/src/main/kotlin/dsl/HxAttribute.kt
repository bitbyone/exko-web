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

    operator fun Pair<String, String>.unaryPlus() {
        val (name, value) = this
        add(object : HxAttribute {
            override val name: String
                get() = "hx-$name"

            override fun content(): String = value
        })
    }
}

fun Component.hx(
    attributes: HxAttributes.() -> Unit
) {
    val attrs = HxAttributes()
    attrs.attributes()
    attrs.apply(this)
}
