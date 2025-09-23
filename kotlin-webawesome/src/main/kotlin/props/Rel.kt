package io.exko.webawesome.props

import kotlinx.html.Tag

interface RelAware : Tag {

    companion object {
        const val ATTR_NAME = "rel"
    }

    var rel: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
