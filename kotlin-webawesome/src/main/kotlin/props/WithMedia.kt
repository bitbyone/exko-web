package io.exko.webawesome.props

import kotlinx.html.Tag

interface WithMediaAware : Tag {

    companion object {
        const val ATTR_NAME = "with-media"
    }

    var withMedia: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
