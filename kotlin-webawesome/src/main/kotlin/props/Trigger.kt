package io.exko.webawesome.props

import io.exko.html.EnumSetAttribute
import kotlinx.html.AttributeEnum
import kotlinx.html.Tag

enum class Trigger(override val realValue: String) : AttributeEnum {
    click("click"),
    hover("hover"),
    focus("focus"),
    manual("manual");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }

    // Operators to mirror Appearance's ergonomics
    operator fun plus(trigger: Trigger): Set<Trigger> = setOf(this, trigger)
    operator fun unaryPlus(): Set<Trigger> = setOf(this)
}

interface TriggerAware : Tag {

    companion object {
        const val ATTR_NAME = "trigger"
    }

    var trigger: Set<Trigger>
        get() = triggerAttr[this, ATTR_NAME]
        set(value) {
            triggerAttr[this, ATTR_NAME] = value
        }
}

internal val triggerAttr = EnumSetAttribute(Trigger.valuesMap)
