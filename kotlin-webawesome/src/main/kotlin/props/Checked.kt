package io.exko.webawesome.props

import kotlinx.html.Tag

interface CheckedAware : Tag {

    companion object {
        const val ATTR_NAME = "checked"
    }

    var checked: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}

