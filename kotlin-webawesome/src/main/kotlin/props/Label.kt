package io.exko.webawesome.props

import kotlinx.html.Tag

interface LabelAware : Tag {

    companion object {
        const val ATTR_NAME = "label"
    }

    var label: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
