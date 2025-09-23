package io.exko.webawesome.props

import kotlinx.html.Tag

interface WithClearAware : Tag {

    companion object {
        const val ATTR_NAME = "with-clear"
    }

    var withClear: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
