package io.exko.htmx.dsl

import io.exko.html.*
import org.intellij.lang.annotations.Language

class HxTarget : HxAttribute {
    override val name: String = "hx-target"
    private var value: String = ""

    override fun content(): String {
        return value
    }

    fun thisPlace(): HxTarget {
        this.value = "this"
        return this
    }

    fun value(@Language("css") selector: String): HxTarget {
        this.value = selector
        return this
    }

    fun next(selector: String): HxTarget {
        this.value = "next $selector"
        return this
    }

    fun next(): HxTarget {
        this.value = "next"
        return this
    }

    fun closest(selector: String): HxTarget {
        this.value = "closest $selector"
        return this
    }

    fun find(selector: String): HxTarget {
        this.value = "find $selector"
        return this
    }

    fun previous(selector: String): HxTarget {
        this.value = "previous $selector"
        return this
    }

    fun previous(): HxTarget {
        this.value = "previous"
        return this
    }
}

fun HxAttributes.target(target: HxTarget.() -> Unit) {
    val hxTarget = HxTarget()
    hxTarget.target()
    add(hxTarget)
}

fun HxAttributes.target(target: String) {
    add(HxTarget().value(target))
}

fun HxAttributes.target(id: Id) {
    add(HxTarget().value(id.cssSelector()))
}

fun HxAttributes.target(cssClass: CssClass) {
    add(HxTarget().value(cssClass.cssSelector()))
}
