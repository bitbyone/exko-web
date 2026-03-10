package io.exko.htmx.dsl

import io.exko.html.CssClass
import io.exko.html.Id
import io.exko.html.cssSelector
import org.intellij.lang.annotations.Language

// hx-include — include extra elements in the request parameters (in addition to the triggering element)
class HxInclude : HxAttribute {
    override val name: String = "hx-include"
    private var selector: String = ""

    override fun content(): String = selector

    fun value(@Language("css") selector: String): HxInclude { this.selector = selector; return this }
    fun id(id: Id): HxInclude = value(id.cssSelector())
    fun cssClass(cssClass: CssClass): HxInclude = value(cssClass.cssSelector())
    fun thisPlace(): HxInclude { selector = "this"; return this }
    fun closestForm(): HxInclude { selector = "closest form"; return this }
    fun closest(@Language("css") selector: String): HxInclude { this.selector = "closest $selector"; return this }
    fun find(@Language("css") selector: String): HxInclude { this.selector = "find $selector"; return this }
    fun next(@Language("css") selector: String): HxInclude { this.selector = "next $selector"; return this }
    fun previous(@Language("css") selector: String): HxInclude { this.selector = "previous $selector"; return this }
}

fun HxAttributes.include(@Language("css") selector: String) {
    add(HxInclude().value(selector))
}

fun HxAttributes.include(id: Id) {
    add(HxInclude().id(id))
}

fun HxAttributes.include(cssClass: CssClass) {
    add(HxInclude().cssClass(cssClass))
}

fun HxAttributes.include(configure: HxInclude.() -> Unit) {
    val hxInclude = HxInclude()
    hxInclude.configure()
    add(hxInclude)
}

fun HxAttributes.includeClosestForm() {
    add(HxInclude().closestForm())
}
