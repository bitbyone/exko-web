package io.exko.webawesome.props

import kotlinx.html.Tag

interface FormActionAware : Tag {

    companion object {
        const val ATTR_NAME = "formaction"
    }

    var formAction: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
