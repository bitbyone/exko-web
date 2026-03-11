package io.exko.webawesome.props

import kotlinx.html.Tag

interface AcceptAware : Tag {

    companion object {
        const val ATTR_NAME = "accept"
    }

    var accept: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
