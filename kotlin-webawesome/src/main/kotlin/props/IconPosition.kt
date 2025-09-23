package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class IconPlacement(override val realValue: String) : AttributeEnum {
    start("start"),
    end("end");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

interface IconPlacementAware : Tag {

    companion object {
        const val ATTR_NAME = "icon-placement"
    }

    var iconPlacement: IconPlacement
        get() = iconPlacementAttr[this, ATTR_NAME]
        set(value) {
            iconPlacementAttr[this, ATTR_NAME] = value
        }
}

internal val iconPlacementAttr = EnumAttribute(IconPlacement.valuesMap)
