package io.exko.htmx.dsl

import io.exko.html.CssClass
import io.exko.html.Id
import io.exko.html.cssSelector
import org.intellij.lang.annotations.Language

// hx-sync="<selector>:<strategy>"
// Synchronizes AJAX requests made from multiple elements against a common selector.
class HxSync : HxAttribute {
    override val name: String = "hx-sync"

    private var selector: String = ""
    private var strategy: String? = null

    override fun content(): String {
        return if (strategy != null) "$selector:$strategy" else selector
    }

    fun on(@Language("css") selector: String): HxSync { this.selector = selector; return this }
    fun on(id: Id): HxSync = on(id.cssSelector())
    fun on(cssClass: CssClass): HxSync = on(cssClass.cssSelector())
    fun onClosestForm(): HxSync { selector = "closest form"; return this }
    fun onThis(): HxSync { selector = "this"; return this }

    // Sync strategies
    fun drop(): HxSync { strategy = "drop"; return this }
    fun abort(): HxSync { strategy = "abort"; return this }
    fun replace(): HxSync { strategy = "replace"; return this }
    fun queue(): HxSync { strategy = "queue"; return this }
    fun queueFirst(): HxSync { strategy = "queue first"; return this }
    fun queueLast(): HxSync { strategy = "queue last"; return this }
}

fun HxAttributes.sync(configure: HxSync.() -> Unit) {
    val hxSync = HxSync()
    hxSync.configure()
    add(hxSync)
}

// Common sync shortcuts
fun HxAttributes.syncClosestFormAbort() = sync { onClosestForm().abort() }
fun HxAttributes.syncClosestFormReplace() = sync { onClosestForm().replace() }
fun HxAttributes.syncClosestFormDrop() = sync { onClosestForm().drop() }
