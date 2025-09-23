package io.exko.webawesome.props

import kotlinx.html.Tag

interface PillAware : Tag {

    companion object {
        const val ATTR_NAME = "pill"
    }

    var pill: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
