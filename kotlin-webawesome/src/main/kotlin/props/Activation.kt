package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Activation mode for wa-tab-group.
 */
enum class Activation(override val realValue: String) : AttributeEnum {
    auto("auto"),
    manual("manual");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

interface ActivationAware : Tag {

    companion object {
        const val ATTR_NAME = "activation"
    }

    var activation: Activation
        get() = activationAttr[this, ATTR_NAME]
        set(value) {
            activationAttr[this, ATTR_NAME] = value
        }
}

internal val activationAttr = EnumAttribute(Activation.valuesMap)
