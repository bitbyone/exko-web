package io.exko.webawesome.props

import kotlinx.html.Tag

interface LoadingAware : Tag {

    companion object {
        const val ATTR_NAME = "loading"
    }

    var loading: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
