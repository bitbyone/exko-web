package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class InputType(override val realValue: String) : AttributeEnum {
    date("date"),
    datetimeLocal("datetime-local"),
    email("email"),
    number("number"),
    password("password"),
    search("search"),
    tel("tel"),
    text("text"),
    time("time"),
    url("url");

    companion object {
        val valuesMap = InputType.entries.associateBy { it.realValue }
    }
}

internal val inputTypeAttr = EnumAttribute(InputType.valuesMap)

interface InputTypeAware : Tag {
    companion object {
        const val ATTR_NAME = "type"
    }

    var type: InputType
        get() = inputTypeAttr[this, ATTR_NAME]
        set(value) {
            inputTypeAttr[this, ATTR_NAME] = value
        }
}
