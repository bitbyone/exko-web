package io.exko.webawesome.props

import io.exko.html.EnumSetAttribute
import kotlinx.html.AttributeEnum
import kotlinx.html.Tag

enum class Appearance(override val realValue: String) : AttributeEnum {

    accent("accent"),
    filled("filled"),
    outlined("outlined"),
    plain("plain");

    companion object {
        val valuesMap = Appearance.entries.associateBy { it.realValue }
    }

    operator fun plus(appearance: Appearance): Set<Appearance> = setOf(this, appearance)
    operator fun unaryPlus(): Set<Appearance> = setOf(this)
}

interface AppearanceAware : Tag {

    companion object {
        const val ATTR_NAME = "appearance"
    }

    var appearance: Set<Appearance>
        get() = appearanceAttr[this, ATTR_NAME]
        set(value) {
            appearanceAttr[this, ATTR_NAME] = value
        }
}

internal val appearanceAttr = EnumSetAttribute(Appearance.valuesMap)
