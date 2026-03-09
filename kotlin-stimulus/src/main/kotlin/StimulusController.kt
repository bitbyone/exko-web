package io.exko.stimulus

open class StimulusController(
    val name: String,
    val code: ControllerConfig.() -> String,
)

data class ControllerConfig(val name: String)
