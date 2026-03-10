package io.exko.htmx.dsl

import io.exko.html.CssClass
import io.exko.html.Id
import io.exko.html.cssSelector

class HxSwap : HxAttribute {
    override val name: String
        get() = if (inherited) "hx-swap:inherited" else "hx-swap"

    private var strategy: String = "innerHTML"
    private val modifiers = mutableListOf<String>()
    private var inherited: Boolean = false

    override fun content(): String {
        return if (modifiers.isEmpty()) strategy else "$strategy ${modifiers.joinToString(" ")}"
    }

    // Swap strategies
    fun innerHTML(): HxSwap { strategy = "innerHTML"; return this }
    fun outerHTML(): HxSwap { strategy = "outerHTML"; return this }
    fun textContent(): HxSwap { strategy = "textContent"; return this }
    fun beforebegin(): HxSwap { strategy = "beforebegin"; return this }
    fun afterbegin(): HxSwap { strategy = "afterbegin"; return this }
    fun beforeend(): HxSwap { strategy = "beforeend"; return this }
    fun afterend(): HxSwap { strategy = "afterend"; return this }
    fun delete(): HxSwap { strategy = "delete"; return this }
    fun none(): HxSwap { strategy = "none"; return this }

    // Timing modifiers
    fun swapDelay(delay: String): HxSwap { modifiers.add("swap:$delay"); return this }
    fun swapDelay(ms: Int): HxSwap = swapDelay("${ms}ms")
    fun settle(delay: String): HxSwap { modifiers.add("settle:$delay"); return this }
    fun settle(ms: Int): HxSwap = settle("${ms}ms")

    // View Transitions API
    fun transition(enabled: Boolean = true): HxSwap { modifiers.add("transition:$enabled"); return this }

    // Scroll modifiers — scroll the target element
    fun scroll(direction: ScrollDirection): HxSwap { modifiers.add("scroll:${direction.value}"); return this }
    fun scroll(selector: String, direction: ScrollDirection): HxSwap {
        modifiers.add("scroll:$selector:${direction.value}"); return this
    }
    fun scroll(id: Id, direction: ScrollDirection): HxSwap = scroll(id.cssSelector(), direction)
    fun scroll(cssClass: CssClass, direction: ScrollDirection): HxSwap = scroll(cssClass.cssSelector(), direction)
    fun scrollWindow(direction: ScrollDirection): HxSwap = scroll("window", direction)

    // Show modifiers — scroll viewport so element is visible
    fun show(direction: ScrollDirection): HxSwap { modifiers.add("show:${direction.value}"); return this }
    fun show(selector: String, direction: ScrollDirection): HxSwap {
        modifiers.add("show:$selector:${direction.value}"); return this
    }
    fun show(id: Id, direction: ScrollDirection): HxSwap = show(id.cssSelector(), direction)
    fun show(cssClass: CssClass, direction: ScrollDirection): HxSwap = show(cssClass.cssSelector(), direction)
    fun showWindow(direction: ScrollDirection): HxSwap = show("window", direction)
    fun showNone(): HxSwap { modifiers.add("show:none"); return this }

    // Focus scroll
    fun focusScroll(enabled: Boolean = true): HxSwap { modifiers.add("focus-scroll:$enabled"); return this }

    // Title handling
    fun ignoreTitle(enabled: Boolean = true): HxSwap { modifiers.add("ignoreTitle:$enabled"); return this }

    // HTMX 4: explicit inheritance
    fun inherited(): HxSwap { inherited = true; return this }

    enum class ScrollDirection(val value: String) {
        TOP("top"),
        BOTTOM("bottom")
    }
}

fun HxAttributes.swap(configure: HxSwap.() -> Unit) {
    val hxSwap = HxSwap()
    hxSwap.configure()
    add(hxSwap)
}

// Convenience shortcuts
fun HxAttributes.swapInnerHTML() = swap { innerHTML() }
fun HxAttributes.swapOuterHTML() = swap { outerHTML() }
fun HxAttributes.swapTextContent() = swap { textContent() }
fun HxAttributes.swapBeforeend() = swap { beforeend() }
fun HxAttributes.swapAfterend() = swap { afterend() }
fun HxAttributes.swapBeforebegin() = swap { beforebegin() }
fun HxAttributes.swapAfterbegin() = swap { afterbegin() }
fun HxAttributes.swapDelete() = swap { delete() }
fun HxAttributes.swapNone() = swap { none() }
