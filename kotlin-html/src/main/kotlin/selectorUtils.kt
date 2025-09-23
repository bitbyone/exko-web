package io.exko.html


val String.cssClass: CssClass
    get() = CssClass(this)

val String.id: Id
    get() = Id(this)

fun Id.cssSelector() = "#${this.value}"
fun CssClass.cssSelector() = ".${this.value}"

@JvmInline
value class Id(val value: String)

@JvmInline
value class CssClass(val value: String)
