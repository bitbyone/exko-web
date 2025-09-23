package io.exko.htmx.dsl

import io.exko.html.*

class HxTrigger : HxAttribute {
    override val name: String = "hx-trigger"
    private var value: String = ""

    override fun content(): String {
        return value
    }

    // Set a custom event name
    fun event(eventName: String): HxTrigger {
        this.value = eventName
        return this
    }

    // Standard events
    fun click(): HxTrigger = event("click")
    fun mouseenter(): HxTrigger = event("mouseenter")
    fun mouseleave(): HxTrigger = event("mouseleave")
    fun change(): HxTrigger = event("change")
    fun submit(): HxTrigger = event("submit")
    fun load(): HxTrigger = event("load")
    fun revealed(): HxTrigger = event("revealed")
    fun intersect(): HxTrigger = event("intersect")
    fun keyup(): HxTrigger = event("keyup")
    fun keydown(): HxTrigger = event("keydown")
    fun focus(): HxTrigger = event("focus")
    fun blur(): HxTrigger = event("blur")

    // Key events with specific keys
    fun keyup(key: String): HxTrigger {
        this.value = "keyup[key=='$key']"
        return this
    }

    fun keydown(key: String): HxTrigger {
        this.value = "keydown[key=='$key']"
        return this
    }

    // Modifiers
    fun once(): HxTrigger {
        this.value = "$value once"
        return this
    }

    fun changed(): HxTrigger {
        this.value = "$value changed"
        return this
    }

    fun delay(milliseconds: Int): HxTrigger {
        this.value = "$value delay:${milliseconds}ms"
        return this
    }

    fun throttle(milliseconds: Int): HxTrigger {
        this.value = "$value throttle:${milliseconds}ms"
        return this
    }

    fun from(selector: String): HxTrigger {
        this.value = "$value from:$selector"
        return this
    }

    fun from(id: Id): HxTrigger {
        return from(id.cssSelector())
    }

    fun from(cssClass: CssClass): HxTrigger {
        return from(cssClass.cssSelector())
    }

    fun target(selector: String): HxTrigger {
        this.value = "$value target:$selector"
        return this
    }

    fun target(id: Id): HxTrigger {
        return target(id.cssSelector())
    }

    fun target(cssClass: CssClass): HxTrigger {
        return target(cssClass.cssSelector())
    }

    fun consume(): HxTrigger {
        this.value = "$value consume"
        return this
    }

    fun queue(queueName: String = "last"): HxTrigger {
        this.value = "$value queue:$queueName"
        return this
    }

    // Filter conditions
    fun filter(condition: String): HxTrigger {
        // If the value already contains a filter, we need to add the new condition with an 'and'
        if (value.contains("[") && value.contains("]")) {
            val openBracketIndex = value.indexOf("[")
            val closeBracketIndex = value.lastIndexOf("]")
            val prefix = value.substring(0, openBracketIndex + 1)
            val existingCondition = value.substring(openBracketIndex + 1, closeBracketIndex)
            val suffix = value.substring(closeBracketIndex)

            this.value = prefix + existingCondition + " && " + condition + suffix
        } else {
            this.value = "$value[$condition]"
        }
        return this
    }

    // Polling
    fun every(seconds: Int): HxTrigger {
        this.value = "every ${seconds}s"
        return this
    }

    fun every(duration: String): HxTrigger {
        this.value = "every $duration"
        return this
    }

    fun everyWithCondition(seconds: Int, condition: String): HxTrigger {
        this.value = "every ${seconds}s[$condition]"
        return this
    }

    fun everyWithCondition(duration: String, condition: String): HxTrigger {
        this.value = "every $duration[$condition]"
        return this
    }

    // Intersection observer options
    fun intersectRoot(selector: String): HxTrigger {
        this.value = "$value root:$selector"
        return this
    }

    fun intersectThreshold(threshold: Double): HxTrigger {
        this.value = "$value threshold:$threshold"
        return this
    }

    fun intersectRootMargin(margin: String): HxTrigger {
        this.value = "$value root-margin:$margin"
        return this
    }

    // Multiple triggers
    fun and(eventName: String): HxTrigger {
        this.value = "$value, $eventName"
        return this
    }

    // Add another trigger with a builder pattern
    fun and(configure: HxTrigger.() -> Unit): HxTrigger {
        val newTrigger = HxTrigger()
        newTrigger.configure()
        this.value = "$value, ${newTrigger.content()}"
        return this
    }
}

// Extension functions for HxAttributes
fun HxAttributes.trigger(trigger: HxTrigger.() -> Unit) {
    val hxTrigger = HxTrigger()
    hxTrigger.trigger()
    add(hxTrigger)
}

fun HxAttributes.trigger(triggerValue: String) {
    add(HxTrigger().event(triggerValue))
}

// Common event triggers
fun HxAttributes.triggerClick() {
    add(HxTrigger().click())
}

fun HxAttributes.triggerClickOnce() {
    add(HxTrigger().click().once())
}

fun HxAttributes.triggerChange() {
    add(HxTrigger().change())
}

fun HxAttributes.triggerSubmit() {
    add(HxTrigger().submit())
}

fun HxAttributes.triggerLoad() {
    add(HxTrigger().load())
}

fun HxAttributes.triggerRevealed() {
    add(HxTrigger().revealed())
}

fun HxAttributes.triggerMouseEnter() {
    add(HxTrigger().mouseenter())
}

fun HxAttributes.triggerMouseLeave() {
    add(HxTrigger().mouseleave())
}

// Key event triggers
fun HxAttributes.triggerKeyUp(key: String) {
    add(HxTrigger().keyup(key))
}

fun HxAttributes.triggerKeyDown(key: String) {
    add(HxTrigger().keydown(key))
}

fun HxAttributes.triggerEnterKey() {
    add(HxTrigger().keydown("Enter"))
}

fun HxAttributes.triggerEscapeKey() {
    add(HxTrigger().keydown("Escape"))
}

// Polling triggers
fun HxAttributes.triggerEvery(seconds: Int) {
    add(HxTrigger().every(seconds))
}

fun HxAttributes.triggerEvery(duration: String) {
    add(HxTrigger().every(duration))
}

fun HxAttributes.triggerEveryWithCondition(seconds: Int, condition: String) {
    add(HxTrigger().everyWithCondition(seconds, condition))
}

// Delayed triggers
fun HxAttributes.triggerWithDelay(eventName: String, milliseconds: Int) {
    add(HxTrigger().event(eventName).delay(milliseconds))
}

fun HxAttributes.triggerClickWithDelay(milliseconds: Int) {
    add(HxTrigger().click().delay(milliseconds))
}

// Throttled triggers
fun HxAttributes.triggerWithThrottle(eventName: String, milliseconds: Int) {
    add(HxTrigger().event(eventName).throttle(milliseconds))
}

fun HxAttributes.triggerClickWithThrottle(milliseconds: Int) {
    add(HxTrigger().click().throttle(milliseconds))
}

// From and Target modifiers with Id and CssClass
fun HxAttributes.triggerFrom(eventName: String, selector: String) {
    add(HxTrigger().event(eventName).from(selector))
}

fun HxAttributes.triggerFrom(eventName: String, id: Id) {
    add(HxTrigger().event(eventName).from(id))
}

fun HxAttributes.triggerFrom(eventName: String, cssClass: CssClass) {
    add(HxTrigger().event(eventName).from(cssClass))
}

fun HxAttributes.triggerTarget(eventName: String, selector: String) {
    add(HxTrigger().event(eventName).target(selector))
}

fun HxAttributes.triggerTarget(eventName: String, id: Id) {
    add(HxTrigger().event(eventName).target(id))
}

fun HxAttributes.triggerTarget(eventName: String, cssClass: CssClass) {
    add(HxTrigger().event(eventName).target(cssClass))
}
