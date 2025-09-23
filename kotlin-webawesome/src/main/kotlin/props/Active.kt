package io.exko.webawesome.props

import kotlinx.html.Tag

interface ActiveAware : Tag {

    companion object {
        const val ATTR_NAME = "active"
    }

    var active: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
