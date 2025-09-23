package io.exko.webawesome.props

import kotlinx.html.Tag

interface PlaceholderAware : Tag {

    companion object {
        const val ATTR_NAME = "placeholder"
    }

    var placeholder: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
