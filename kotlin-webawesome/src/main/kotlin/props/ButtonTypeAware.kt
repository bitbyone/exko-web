package io.exko.webawesome.props

import kotlinx.html.ButtonType
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

interface ButtonTypeAware : Tag {

    companion object {
        const val ATTR_NAME = "type"
    }

    var type: ButtonType
        get() = buttonTypeAttr[this, ATTR_NAME]
        set(value) {
            buttonTypeAttr[this, ATTR_NAME] = value
        }
}

internal val buttonTypesValuesMap = ButtonType.entries.associateBy { it.realValue }

internal val buttonTypeAttr = EnumAttribute(buttonTypesValuesMap)
