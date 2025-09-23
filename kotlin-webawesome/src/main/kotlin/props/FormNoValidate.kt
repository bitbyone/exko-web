package io.exko.webawesome.props

import kotlinx.html.Tag

interface FormNoValidateAware : Tag {

    companion object {
        const val ATTR_NAME = "formnovalidate"
    }

    var formNoValidate: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
