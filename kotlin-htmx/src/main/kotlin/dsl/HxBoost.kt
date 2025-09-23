package io.exko.htmx.dsl

class HxBoost : HxAttribute {
    override val name = "hx-boost"
    private var value = "true"

    override fun content(): String {
        return value
    }

    fun boost(enabled: Boolean): HxBoost {
        value = "$enabled"
        return this
    }
}

fun HxAttributes.boost(enabled: Boolean) {
    add(HxBoost().boost(enabled))
}
