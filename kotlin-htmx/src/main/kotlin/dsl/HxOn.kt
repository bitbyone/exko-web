package io.exko.htmx.dsl

import org.intellij.lang.annotations.Language

// hx-on:<event> — inline event handler for htmx events
// htmx 4 event naming: htmx:phase:action (e.g. htmx:before:request)

private class HxOnAttr(event: String, private val handler: String) : HxAttribute {
    override val name: String = "hx-on:$event"
    override fun content(): String = handler
}

// Phase-specific action scopes — only valid actions are exposed per phase

class HxOnBefore(private val attrs: HxAttributes) {
    fun init(handler: () -> String) { attrs.add(HxOnAttr("htmx:before:init", handler())) }
    fun cleanup(handler: () -> String) { attrs.add(HxOnAttr("htmx:before:cleanup", handler())) }
    fun request(handler: () -> String) { attrs.add(HxOnAttr("htmx:before:request", handler())) }
    fun response(handler: () -> String) { attrs.add(HxOnAttr("htmx:before:response", handler())) }
    fun swap(handler: () -> String) { attrs.add(HxOnAttr("htmx:before:swap", handler())) }
    fun restoreHistory(handler: () -> String) { attrs.add(HxOnAttr("htmx:before:restore:history", handler())) }
    fun historyUpdate(handler: () -> String) { attrs.add(HxOnAttr("htmx:before:history:update", handler())) }
    fun viewTransition(handler: () -> String) { attrs.add(HxOnAttr("htmx:before:viewTransition", handler())) }
}

class HxOnAfter(private val attrs: HxAttributes) {
    fun init(handler: () -> String) { attrs.add(HxOnAttr("htmx:after:init", handler())) }
    fun cleanup(handler: () -> String) { attrs.add(HxOnAttr("htmx:after:cleanup", handler())) }
    fun request(handler: () -> String) { attrs.add(HxOnAttr("htmx:after:request", handler())) }
    fun swap(handler: () -> String) { attrs.add(HxOnAttr("htmx:after:swap", handler())) }
    fun historyUpdate(handler: () -> String) { attrs.add(HxOnAttr("htmx:after:history:update", handler())) }
    fun pushIntoHistory(handler: () -> String) { attrs.add(HxOnAttr("htmx:after:push:into:history", handler())) }
    fun replaceIntoHistory(handler: () -> String) { attrs.add(HxOnAttr("htmx:after:replace:into:history", handler())) }
    fun viewTransition(handler: () -> String) { attrs.add(HxOnAttr("htmx:after:viewTransition", handler())) }
}

class HxOnConfig(private val attrs: HxAttributes) {
    fun request(handler: () -> String) { attrs.add(HxOnAttr("htmx:config:request", handler())) }
}

class HxOnFinally(private val attrs: HxAttributes) {
    fun request(handler: () -> String) { attrs.add(HxOnAttr("htmx:finally:request", handler())) }
}

class HxOnScope(private val attrs: HxAttributes) {
    fun before() = HxOnBefore(attrs)
    fun after() = HxOnAfter(attrs)
    fun config() = HxOnConfig(attrs)
    fun finally() = HxOnFinally(attrs)

    fun abort(handler: () -> String) { attrs.add(HxOnAttr("htmx:abort", handler())) }
    fun error(handler: () -> String) { attrs.add(HxOnAttr("htmx:error", handler())) }
    fun confirm(handler: () -> String) { attrs.add(HxOnAttr("htmx:confirm", handler())) }
}

// Variant 1: block — multiple events
// hx { on { before().request { "..." }; after().request { "..." } } }
fun HxAttributes.on(block: HxOnScope.() -> Unit) {
    HxOnScope(this).block()
}

// Variant 2: inline — single event
// hx { on().before().request { "..." } }
fun HxAttributes.on(): HxOnScope = HxOnScope(this)
