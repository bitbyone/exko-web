package io.exko.webawesome.props

import kotlinx.html.Tag

interface SkiddingAware : Tag {

    companion object {
        const val ATTR_NAME = "skidding"
    }

    var skidding: Int
        get() = intAttr[this, ATTR_NAME]
        set(value) {
            intAttr[this, ATTR_NAME] = value
        }
}
