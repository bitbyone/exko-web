package io.exko.webawesome.props

import kotlinx.html.Tag

interface FormTargetAware : Tag {

    companion object {
        const val ATTR_NAME = "formtarget"
    }

    var formTarget: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
