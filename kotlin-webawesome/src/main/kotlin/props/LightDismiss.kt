package io.exko.webawesome.props

import kotlinx.html.Tag

interface LightDismissAware : Tag {

    companion object {
        const val ATTR_NAME = "light-dismiss"
    }

    var lightDismiss: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
