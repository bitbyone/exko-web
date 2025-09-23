package io.exko.webawesome.props

import kotlinx.html.FormMethod
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute


interface FormMethodAware : Tag {

    companion object {
        const val ATTR_NAME = "formmethod"
    }

    var formMethod: FormMethod
        get() = formMethodAttr[this, ATTR_NAME]
        set(value) {
            formMethodAttr[this, ATTR_NAME] = value
        }
}

internal val formMethodValuesMap = FormMethod.entries.associateBy { it.realValue }

internal val formMethodAttr = EnumAttribute(formMethodValuesMap)
