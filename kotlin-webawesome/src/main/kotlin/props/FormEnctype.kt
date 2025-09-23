package io.exko.webawesome.props

import kotlinx.html.FormEncType
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute


interface FormEnctypeAware : Tag {

    companion object {
        const val ATTR_NAME = "formenctype"
    }

    var formEnctype: FormEncType
        get() = formEnctypeAttr[this, ATTR_NAME]
        set(value) {
            formEnctypeAttr[this, ATTR_NAME] = value
        }
}

internal val formEnctypeValuesMap = FormEncType.entries.associateBy { it.realValue }
internal val formEnctypeAttr = EnumAttribute(formEnctypeValuesMap)
