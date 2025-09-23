package io.exko.webawesome.props

import kotlinx.html.Tag

interface DisabledAware : Tag {

    companion object {
        const val ATTR_NAME = "disabled"
    }

    var disabled: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
