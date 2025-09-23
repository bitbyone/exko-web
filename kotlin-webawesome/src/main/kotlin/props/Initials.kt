package io.exko.webawesome.props

import kotlinx.html.Tag

interface InitialsAware : Tag {

    companion object {
        const val ATTR_NAME = "initials"
    }

    var initials: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
