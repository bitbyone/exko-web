package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class Variant(override val realValue: String) : AttributeEnum {

    neutral("neutral"),
    brand("brand"),
    success("success"),
    warning("warning"),
    danger("danger");

    companion object {
        val valuesMap = Variant.entries.associateBy { it.realValue }
    }
}

interface VariantAware : Tag {

    companion object {
        const val ATTR_NAME = "variant"
    }

    var variant: Variant
        get() = variantsAttr[this, ATTR_NAME]
        set(value) {
            variantsAttr[this, ATTR_NAME] = value
        }
}

internal val variantsAttr = EnumAttribute(Variant.valuesMap)
