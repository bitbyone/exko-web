package io.exko.webawesome.props

import kotlinx.html.Tag

interface HrefAware : Tag {

    companion object {
        const val ATTR_NAME = "href"
    }

    var href: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
