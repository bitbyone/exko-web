package io.exko.webawesome.props

import kotlinx.html.Tag

interface RequiredAware : Tag {

    companion object {
        const val ATTR_NAME = "required"
    }

    var required: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
