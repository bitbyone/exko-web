package io.exko.webawesome.components.dropdown.attr

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class DropdownItemVariant(override val realValue: String) : AttributeEnum {
    danger("danger"),
    default("default");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

interface DropdownItemVariantAware : Tag {

    companion object {
        const val ATTR_NAME = "variant"
    }

    var variant: DropdownItemVariant
        get() = dropdownItemVariantAttr[this, ATTR_NAME]
        set(value) {
            dropdownItemVariantAttr[this, ATTR_NAME] = value
        }
}

internal val dropdownItemVariantAttr = EnumAttribute(DropdownItemVariant.valuesMap)

