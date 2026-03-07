package io.exko.webawesome.props

import kotlinx.html.Tag

interface DurationAware : Tag {

    companion object {
        const val ATTR_NAME = "duration"
    }

    var duration: Int
        get() = intAttr[this, ATTR_NAME]
        set(value) {
            intAttr[this, ATTR_NAME] = value
        }
}
