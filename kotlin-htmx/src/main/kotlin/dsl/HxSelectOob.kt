package io.exko.htmx.dsl

import io.exko.html.CssClass
import io.exko.html.Id
import io.exko.html.cssSelector
import org.intellij.lang.annotations.Language

// hx-select-oob — pick multiple out-of-band elements from the response by CSS selector
// Format: "#id1,#id2:outerHTML,#id3:afterend"  (selector optionally followed by :swap-strategy)
class HxSelectOob : HxAttribute {
    override val name: String = "hx-select-oob"
    private val entries = mutableListOf<String>()

    override fun content(): String = entries.joinToString(",")

    fun select(@Language("css") selector: String): HxSelectOob { entries.add(selector); return this }
    fun select(id: Id): HxSelectOob = select(id.cssSelector())
    fun select(cssClass: CssClass): HxSelectOob = select(cssClass.cssSelector())

    // Select with explicit swap strategy
    fun select(@Language("css") selector: String, swapStrategy: String): HxSelectOob {
        entries.add("$selector:$swapStrategy"); return this
    }
    fun select(id: Id, swapStrategy: String): HxSelectOob = select(id.cssSelector(), swapStrategy)
    fun select(cssClass: CssClass, swapStrategy: String): HxSelectOob = select(cssClass.cssSelector(), swapStrategy)
}

fun HxAttributes.selectOob(configure: HxSelectOob.() -> Unit) {
    val hxSelectOob = HxSelectOob()
    hxSelectOob.configure()
    add(hxSelectOob)
}

fun HxAttributes.selectOob(@Language("css") vararg selectors: String) {
    val oob = HxSelectOob()
    selectors.forEach { oob.select(it) }
    add(oob)
}
