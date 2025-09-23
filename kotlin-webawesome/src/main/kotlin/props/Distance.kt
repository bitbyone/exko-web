package io.exko.webawesome.props

import kotlinx.html.Tag

interface DistanceAware : Tag {

    companion object {
        const val ATTR_NAME = "distance"
    }

    var distance: Int
        get() = intAttr[this, ATTR_NAME]
        set(value) {
            intAttr[this, ATTR_NAME] = value
        }
}
