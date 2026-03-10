package io.exko.htmx.dsl

import io.exko.html.CssClass
import io.exko.html.Id
import io.exko.html.cssSelector
import org.intellij.lang.annotations.Language

// hx-indicator — element to add the htmx-request class to during the request
// (used to show loading spinners / progress indicators)
class HxIndicator : HxAttribute {
    override val name: String = "hx-indicator"
    private var selector: String = ""

    override fun content(): String = selector

    fun value(@Language("css") selector: String): HxIndicator { this.selector = selector; return this }
    fun id(id: Id): HxIndicator = value(id.cssSelector())
    fun cssClass(cssClass: CssClass): HxIndicator = value(cssClass.cssSelector())
    fun closest(@Language("css") selector: String): HxIndicator { this.selector = "closest $selector"; return this }
    fun find(@Language("css") selector: String): HxIndicator { this.selector = "find $selector"; return this }
}

fun HxAttributes.indicator(@Language("css") selector: String) {
    add(HxIndicator().value(selector))
}

fun HxAttributes.indicator(id: Id) {
    add(HxIndicator().id(id))
}

fun HxAttributes.indicator(cssClass: CssClass) {
    add(HxIndicator().cssClass(cssClass))
}

fun HxAttributes.indicator(configure: HxIndicator.() -> Unit) {
    val hxIndicator = HxIndicator()
    hxIndicator.configure()
    add(hxIndicator)
}
