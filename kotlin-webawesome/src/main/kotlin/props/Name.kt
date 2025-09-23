package io.exko.webawesome.props

import kotlinx.html.Tag

interface NameAware : Tag {

    companion object {
        const val ATTR_NAME = "name"
    }

    var name: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
