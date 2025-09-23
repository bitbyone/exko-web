package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Placement options for wa-tab-group.
 */
enum class TabsPlacement(override val realValue: String) : AttributeEnum {
    top("top"),
    bottom("bottom"),
    start("start"),
    end("end");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

interface TabsPlacementAware : Tag {

    companion object {
        const val ATTR_NAME = "placement"
    }

    var placement: TabsPlacement
        get() = tabsPlacementAttr[this, ATTR_NAME]
        set(value) {
            tabsPlacementAttr[this, ATTR_NAME] = value
        }
}

internal val tabsPlacementAttr = EnumAttribute(TabsPlacement.valuesMap)
