package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Select appearance supports only "filled" and "outlined" according to wa-select.d.ts
 */
enum class SimpleAppearance(override val realValue: String) : AttributeEnum {
    filled("filled"),
    outlined("outlined");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val simpleAppearanceAttr = EnumAttribute(SimpleAppearance.valuesMap)

interface SimpleAppearanceAware : Tag {
    companion object {
        const val ATTR_NAME = "appearance"
    }

    var appearance: SimpleAppearance
        get() = simpleAppearanceAttr[this, ATTR_NAME]
        set(value) {
            simpleAppearanceAttr[this, ATTR_NAME] = value
        }
}
