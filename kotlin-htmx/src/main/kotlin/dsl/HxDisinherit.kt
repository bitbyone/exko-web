package io.exko.htmx.dsl

// hx-disinherit — prevent specific attributes from being inherited by child elements
// Note: In HTMX 4, inheritance is explicit via the `:inherited` modifier,
//       so hx-disinherit is mainly useful when htmx.config.implicitInheritance is enabled.
// Values: "*" (all), or space-separated list of attribute names
class HxDisinherit : HxAttribute {
    override val name: String = "hx-disinherit"
    private val attrs = mutableListOf<String>()

    override fun content(): String = if (attrs.isEmpty()) "*" else attrs.joinToString(" ")

    fun all(): HxDisinherit { attrs.clear(); return this }
    fun attr(attrName: String): HxDisinherit { attrs.add(attrName); return this }

    // Common shortcuts for frequently disinherited attributes
    fun get(): HxDisinherit = attr("hx-get")
    fun post(): HxDisinherit = attr("hx-post")
    fun target(): HxDisinherit = attr("hx-target")
    fun swap(): HxDisinherit = attr("hx-swap")
    fun select(): HxDisinherit = attr("hx-select")
    fun boost(): HxDisinherit = attr("hx-boost")
    fun confirm(): HxDisinherit = attr("hx-confirm")
    fun headers(): HxDisinherit = attr("hx-headers")
    fun vals(): HxDisinherit = attr("hx-vals")
}

fun HxAttributes.disinherit(configure: HxDisinherit.() -> Unit) {
    val hxDisinherit = HxDisinherit()
    hxDisinherit.configure()
    add(hxDisinherit)
}

fun HxAttributes.disinheritAll() {
    add(HxDisinherit().all())
}
