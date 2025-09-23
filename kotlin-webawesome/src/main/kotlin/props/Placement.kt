package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Unified placement options for popovers, tooltips, etc. Mirrors WebAwesome's placement values.
 */
enum class Placement(override val realValue: String) : AttributeEnum {
    top("top"),
    topStart("top-start"),
    topEnd("top-end"),
    right("right"),
    rightStart("right-start"),
    rightEnd("right-end"),
    bottom("bottom"),
    bottomStart("bottom-start"),
    bottomEnd("bottom-end"),
    left("left"),
    leftStart("left-start"),
    leftEnd("left-end");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

interface PlacementAware : Tag {

    companion object {
        const val ATTR_NAME = "placement"
    }

    var placement: Placement
        get() = placementAttr[this, ATTR_NAME]
        set(value) {
            placementAttr[this, ATTR_NAME] = value
        }
}

internal val placementAttr = EnumAttribute(Placement.valuesMap)
