package io.exko.webawesome.props

import kotlinx.html.Tag

interface WithCaretAware : Tag {

    companion object {
        const val ATTR_NAME = "with-caret"
    }

    var withCaret: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
