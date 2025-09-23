package io.exko.webawesome.props

import kotlinx.html.Tag

interface WithoutSpinButtonsAware : Tag {

    companion object {
        const val ATTR_NAME = "without-spin-buttons"
    }

    var withoutSpinButtons: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
