package io.exko.webawesome.props

import kotlinx.html.Tag

interface FormAware : Tag {

    companion object {
        const val ATTR_NAME = "form"
    }

    var form: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
                stringAttr[this, ATTR_NAME] = value
        }
}
