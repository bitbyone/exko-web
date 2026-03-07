package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class ToastPlacement(override val realValue: String) : AttributeEnum {
    topStart("top-start"),
    topCenter("top-center"),
    topEnd("top-end"),
    bottomStart("bottom-start"),
    bottomCenter("bottom-center"),
    bottomEnd("bottom-end");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val toastPlacementAttr = EnumAttribute(ToastPlacement.valuesMap)

interface ToastPlacementAware : Tag {
    companion object {
        const val ATTR_NAME = "placement"
    }

    var placement: ToastPlacement
        get() = toastPlacementAttr[this, ATTR_NAME]
        set(value) {
            toastPlacementAttr[this, ATTR_NAME] = value
        }
}
