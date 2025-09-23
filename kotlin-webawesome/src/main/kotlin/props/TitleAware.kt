package io.exko.webawesome.props

import kotlinx.html.Tag

interface TitleAware : Tag {

    companion object {
        const val ATTR_NAME = "title"
    }

    var title: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
