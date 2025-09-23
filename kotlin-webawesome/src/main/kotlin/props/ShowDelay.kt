package io.exko.webawesome.props

import kotlinx.html.Tag

interface ShowDelayAware : Tag {

    companion object {
        const val ATTR_NAME = "show-delay"
    }

    var showDelay: Int
        get() = intAttr[this, ATTR_NAME]
        set(value) {
            intAttr[this, ATTR_NAME] = value
        }
}
