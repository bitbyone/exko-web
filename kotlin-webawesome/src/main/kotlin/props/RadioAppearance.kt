package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Radio appearance supports only "default" and "button" according to wa-radio.d.ts
 */
enum class RadioAppearance(override val realValue: String) : AttributeEnum {
    default("default"),
    button("button");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val radioAppearanceAttr = EnumAttribute(RadioAppearance.valuesMap)

interface RadioAppearanceAware : Tag {
    companion object {
        const val ATTR_NAME = "appearance"
    }

    var appearance: RadioAppearance
        get() = radioAppearanceAttr[this, ATTR_NAME]
        set(value) {
            radioAppearanceAttr[this, ATTR_NAME] = value
        }
}
