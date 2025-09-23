package io.exko.webawesome.props

import kotlinx.html.Tag

interface WithFooterAware : Tag {

    companion object {
        const val ATTR_NAME = "with-footer"
    }

    var withFooter: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
