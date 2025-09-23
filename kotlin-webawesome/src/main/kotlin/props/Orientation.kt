package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class Orientation(override val realValue: String) : AttributeEnum {
    horizontal("horizontal"),
    vertical("vertical");

    companion object {
        val valuesMap = Orientation.entries.associateBy { it.realValue }
    }
}

interface OrientationAware : Tag {

    companion object {
        const val ATTR_NAME = "orientation"
    }

    var orientation: Orientation
        get() = orientationAttr[this, ATTR_NAME]
        set(value) {
            orientationAttr[this, ATTR_NAME] = value
        }
}

internal val orientationAttr = EnumAttribute(Orientation.valuesMap)
