package io.exko.htmx.dsl

// hx-history — opt an element out of the history snapshot mechanism
// The only meaningful value in practice is "false" to disable snapshots for an element.
class HxHistory : HxAttribute {
    override val name: String = "hx-history"
    private var value: String = "false"

    override fun content(): String = value

    fun disable(): HxHistory { value = "false"; return this }
}

fun HxAttributes.historyDisable() {
    add(HxHistory().disable())
}
