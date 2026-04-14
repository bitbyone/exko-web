package io.exko.stimulus

open class StimulusController(
    val name: String,
    val shared: Boolean,
    val code: ControllerConfig.() -> String,
) {
    constructor(
        name: String,
        code: ControllerConfig.() -> String,
    ) : this(name, true, code)
}

data class ControllerConfig(val name: String)
