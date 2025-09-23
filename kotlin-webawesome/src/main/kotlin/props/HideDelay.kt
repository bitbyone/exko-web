package io.exko.webawesome.props

import kotlinx.html.Tag

interface HideDelayAware : Tag {

    companion object {
        const val ATTR_NAME = "hide-delay"
    }

    var hideDelay: Int
        get() = intAttr[this, ATTR_NAME]
        set(value) {
            intAttr[this, ATTR_NAME] = value
        }
}
