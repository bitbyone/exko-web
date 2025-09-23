package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class Size(override val realValue: String) : AttributeEnum {
    small("small"),
    medium("medium"),
    large("large");

    companion object {
        val valuesMap = Size.entries.associateBy { it.realValue }
    }
}

interface SizeAware : Tag {

    companion object {
        const val ATTR_NAME = "size"
    }

    var size: Size
        get() = sizeAttr[this, ATTR_NAME]
        set(value) {
            sizeAttr[this, ATTR_NAME] = value
        }
}

internal val sizeAttr = EnumAttribute(Size.valuesMap)
