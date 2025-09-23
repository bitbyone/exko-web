package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.attributes.EnumAttribute

/**
 * Simple placement supports only top | right | bottom | left.
 * Used for slider tooltip placement per wa-slider.d.ts
 */
enum class SimplePlacement(override val realValue: String) : AttributeEnum {
    top("top"),
    right("right"),
    bottom("bottom"),
    left("left");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val simplePlacementAttr = EnumAttribute(SimplePlacement.valuesMap)
