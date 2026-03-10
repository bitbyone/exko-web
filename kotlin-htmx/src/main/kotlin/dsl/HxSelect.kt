package io.exko.htmx.dsl

import io.exko.html.CssClass
import io.exko.html.Id
import io.exko.html.cssSelector
import org.intellij.lang.annotations.Language

// hx-select — select a subset of the server response to be swapped in
class HxSelect : HxAttribute {
    override val name: String = "hx-select"
    private var selector: String = ""

    override fun content(): String = selector

    fun value(@Language("css") selector: String): HxSelect { this.selector = selector; return this }
    fun id(id: Id): HxSelect = value(id.cssSelector())
    fun cssClass(cssClass: CssClass): HxSelect = value(cssClass.cssSelector())
}

fun HxAttributes.select(@Language("css") selector: String) {
    add(HxSelect().value(selector))
}

fun HxAttributes.select(id: Id) {
    add(HxSelect().id(id))
}

fun HxAttributes.select(cssClass: CssClass) {
    add(HxSelect().cssClass(cssClass))
}

fun HxAttributes.select(configure: HxSelect.() -> Unit) {
    val hxSelect = HxSelect()
    hxSelect.configure()
    add(hxSelect)
}
