package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class Target(override val realValue: String) : AttributeEnum {
    self("_self"),
    blank("_blank"),
    parent("_parent"),
    top("_top");

    companion object {
        val valuesMap = Target.entries.associateBy { it.realValue }
    }
}

interface TargetAware : Tag {

    companion object {
        const val ATTR_NAME = "target"
    }

    var target: Target
        get() = targetAttr[this, ATTR_NAME]
        set(value) {
            targetAttr[this, ATTR_NAME] = value
        }
}

internal val targetAttr = EnumAttribute(Target.valuesMap)
