package io.exko.webawesome.props

import kotlinx.html.Tag

interface OpenAware : Tag {

    companion object {
        const val ATTR_NAME = "open"
    }

    var open: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
