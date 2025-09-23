package io.exko.webawesome.props

import kotlinx.html.Tag

interface ValueAware : Tag {

    companion object {
        const val ATTR_NAME = "value"
    }

    var value: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
