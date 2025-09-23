package io.exko.webawesome.props

import kotlinx.html.Tag

interface SelectedAware : Tag {

    companion object {
        const val ATTR_NAME = "selected"
    }

    var selected: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
