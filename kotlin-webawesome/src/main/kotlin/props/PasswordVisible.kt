package io.exko.webawesome.props

import kotlinx.html.Tag

interface PasswordVisibleAware : Tag {

    companion object {
        const val ATTR_NAME = "password-visible"
    }

    var passwordVisible: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
