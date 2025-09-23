package io.exko.webawesome.props

import kotlinx.html.Tag

interface ForAware : Tag {

    companion object {
        const val ATTR_NAME = "for"
    }

    var forId: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
