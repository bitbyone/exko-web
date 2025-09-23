package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class Attention(override val realValue: String) : AttributeEnum {
    none("none"),
    pulse("pulse"),
    bounce("bounce");

    companion object {
        val valuesMap = Attention.entries.associateBy { it.realValue }
    }
}

interface AttentionAware : Tag {

    companion object {
        const val ATTR_NAME = "attention"
    }

    var attention: Attention
        get() = attentionAttr[this, ATTR_NAME]
        set(value) {
            attentionAttr[this, ATTR_NAME] = value
        }
}

internal val attentionAttr = EnumAttribute(Attention.valuesMap)
