package io.exko.webawesome.props

import kotlinx.html.Tag

interface ReadonlyAware : Tag {

    companion object {
        const val ATTR_NAME = "readonly"
    }

    var readonly: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
