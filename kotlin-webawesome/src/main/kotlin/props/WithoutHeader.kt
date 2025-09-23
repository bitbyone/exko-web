package io.exko.webawesome.props

import kotlinx.html.Tag

interface WithoutHeaderAware : Tag {

    companion object {
        const val ATTR_NAME = "without-header"
    }

    var withoutHeader: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
