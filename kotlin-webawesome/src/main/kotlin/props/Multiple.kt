package io.exko.webawesome.props

import kotlinx.html.Tag

interface MultipleAware : Tag {

    companion object {
        const val ATTR_NAME = "multiple"
    }

    var multiple: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
