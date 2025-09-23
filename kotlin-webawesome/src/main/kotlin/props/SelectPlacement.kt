package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Select placement supports only "top" and "bottom" according to wa-select.d.ts
 */
enum class SelectPlacement(override val realValue: String) : AttributeEnum {
    top("top"),
    bottom("bottom");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val selectPlacementAttr = EnumAttribute(SelectPlacement.valuesMap)

interface SelectPlacementAware : Tag {
    companion object {
        const val ATTR_NAME = "placement"
    }

    var placement: SelectPlacement
        get() = selectPlacementAttr[this, ATTR_NAME]
        set(value) {
            selectPlacementAttr[this, ATTR_NAME] = value
        }
}
