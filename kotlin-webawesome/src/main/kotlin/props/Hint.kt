package io.exko.webawesome.props

import kotlinx.html.Tag

interface HintAware : Tag {

    companion object {
        const val ATTR_NAME = "hint"
    }

    var hint: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
