package io.exko.htmx.dsl

// hx-params — filter which parameters are included in the request
// Values: "*" (all), "none", "not param1,param2", or "param1,param2"
class HxParams : HxAttribute {
    override val name: String = "hx-params"
    private var value: String = "*"

    override fun content(): String = value

    // Include all parameters (default)
    fun all(): HxParams { value = "*"; return this }

    // Exclude all parameters
    fun none(): HxParams { value = "none"; return this }

    // Exclude specific parameters
    fun not(vararg params: String): HxParams { value = "not ${params.joinToString(",")}"; return this }

    // Include only specific parameters
    fun only(vararg params: String): HxParams { value = params.joinToString(","); return this }
}

fun HxAttributes.params(configure: HxParams.() -> Unit) {
    val hxParams = HxParams()
    hxParams.configure()
    add(hxParams)
}

fun HxAttributes.paramsAll() = add(HxParams().all())
fun HxAttributes.paramsNone() = add(HxParams().none())
fun HxAttributes.paramsNot(vararg params: String) = add(HxParams().not(*params))
fun HxAttributes.paramsOnly(vararg params: String) = add(HxParams().only(*params))
