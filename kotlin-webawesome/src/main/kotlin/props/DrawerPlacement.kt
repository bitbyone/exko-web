package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Drawer placement supports only "top", "end", "bottom", and "start" according to wa-drawer.d.ts
 */
enum class DrawerPlacement(override val realValue: String) : AttributeEnum {
    top("top"),
    end("end"),
    bottom("bottom"),
    start("start");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val drawerPlacementAttr = EnumAttribute(DrawerPlacement.valuesMap)

interface DrawerPlacementAware : Tag {
    companion object {
        const val ATTR_NAME = "placement"
    }

    var placement: DrawerPlacement
        get() = drawerPlacementAttr[this, ATTR_NAME]
        set(value) {
            drawerPlacementAttr[this, ATTR_NAME] = value
        }
}
