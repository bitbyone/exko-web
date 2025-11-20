package io.exko.htmx.dsl

class HxBoost : HxAttribute {
    override val name: String
        get() {
            return if (inherited) "hx-boost:inherited" else "hx-boost"
        }
    private var value = "true"
    private var inherited = false

    override fun content(): String {
        return value
    }

    fun boost(enabled: Boolean, inherited: Boolean = false): HxBoost {
        this.value = "$enabled"
        this.inherited = inherited
        return this
    }
}

fun HxAttributes.boost(enabled: Boolean, inherited: Boolean = false) {
    add(HxBoost().boost(enabled, inherited))
}
