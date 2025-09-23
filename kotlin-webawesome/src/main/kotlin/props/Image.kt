package io.exko.webawesome.props

import kotlinx.html.Tag

interface ImageAware : Tag {

    companion object {
        const val ATTR_NAME = "image"
    }

    var image: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
