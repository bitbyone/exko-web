package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class Appearance(override val realValue: String) : AttributeEnum {

    accent("accent"),
    filled("filled"),
    outlined("outlined"),
    plain("plain"),
    filledOutlined("filled-outlined"),
    ;

    companion object {
        val valuesMap = Appearance.entries.associateBy { it.realValue }
    }
}

interface AppearanceAware : Tag {

    companion object {
        const val ATTR_NAME = "appearance"
    }

    var appearance: Appearance
        get() = appearanceAttr[this, ATTR_NAME]
        set(value) {
            appearanceAttr[this, ATTR_NAME] = value
        }
}

internal val appearanceAttr = EnumAttribute(Appearance.valuesMap)
