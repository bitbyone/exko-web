package io.exko.webawesome.components.dropdown.attr

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class DropdownItemType(override val realValue: String) : AttributeEnum {
    normal("normal"),
    checkbox("checkbox");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

interface DropdownItemTypeAware : Tag {

    companion object {
        const val ATTR_NAME = "type"
    }

    var type: DropdownItemType
        get() = dropdownItemTypeAttr[this, ATTR_NAME]
        set(value) {
            dropdownItemTypeAttr[this, ATTR_NAME] = value
        }
}

internal val dropdownItemTypeAttr = EnumAttribute(DropdownItemType.valuesMap)

