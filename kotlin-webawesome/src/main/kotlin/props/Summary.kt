package io.exko.webawesome.props

import kotlinx.html.Tag

interface SummaryAware : Tag {

    companion object {
        const val ATTR_NAME = "summary"
    }

    var summary: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
