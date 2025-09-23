package io.exko.webawesome.components.dropdown.attr

import io.exko.webawesome.props.tickerAttr
import kotlinx.html.Tag

interface SubmenuOpenAware : Tag {

    companion object {
        const val ATTR_NAME = "submenu-open"
    }

    var submenuOpen: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}

