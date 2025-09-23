package io.exko.webawesome.props

import kotlinx.html.Tag

interface WithHeaderAware : Tag {

    companion object {
        const val ATTR_NAME = "with-header"
    }

    var withHeader: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
