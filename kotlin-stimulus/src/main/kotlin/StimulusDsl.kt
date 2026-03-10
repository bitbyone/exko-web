package io.exko.stimulus

import kotlinx.html.Tag

interface Stimulus {

    fun registerController()

    fun target(targetName: String)

    fun value(valueName: String, v: String)

    fun action(method: String, on: String = "click")

    fun outlet(outletController: StimulusController, selector: String)

    fun outlet(outletName: String, selector: String)

    fun apply(tag: Tag)
}

open class StimulusBinding(val name: String) : Stimulus {

    private val controllers = mutableListOf<String>()
    private val targets = mutableListOf<Pair<String, String>>()
    private val values = mutableListOf<Pair<String, String>>()
    private val actions = mutableListOf<String>()
    private val outlets = mutableListOf<Pair<String, String>>()

    override fun registerController() {
        controllers.add(name)
    }

    override fun target(targetName: String) {
        targets.add("data-$name-target" to targetName)
    }

    override fun value(
        valueName: String,
        v: String,
    ) {
        val kebabName = camelToKebab(valueName)
        values.add("data-$name-$kebabName-value" to v)
    }

    override fun action(
        method: String,
        on: String
    ) {
        actions.add("$on->$name#$method")
    }

    override fun outlet(
        outletController: StimulusController,
        selector: String,
    ) {
        outlets.add("data-$name-${outletController.name}-outlet" to selector)
    }

    override fun outlet(
        outletName: String,
        selector: String,
    ) {
        outlets.add("data-$name-$outletName-outlet" to selector)
    }

    override fun apply(tag: Tag) {
        if (controllers.isNotEmpty()) {
            val existing = tag.attributes["data-controller"]
            val joined = controllers.joinToString(" ")
            tag.attributes["data-controller"] = existing?.let { "$it $joined" } ?: joined
        }
        targets.forEach { (attr, value) ->
            tag.attributes[attr] = value
        }
        values.forEach { (attr, value) ->
            tag.attributes[attr] = value
        }
        if (actions.isNotEmpty()) {
            val existing = tag.attributes["data-action"]
            val joined = actions.joinToString(" ")
            tag.attributes["data-action"] = existing?.let { "$it $joined" } ?: joined
        }
        outlets.forEach { (attr, value) ->
            tag.attributes[attr] = value
        }
    }
}

class StimulusTagScope(val tag: Tag) {

    inline operator fun <T : StimulusController> T.invoke(block: ControllerScope<T>.() -> Unit) {
        val stimulus = ControllerScope(StimulusBinding(name), this)
        stimulus.block()
        stimulus.apply(tag)
    }
}

inline fun <T : StimulusController> Tag.stimulus(
    controller: T,
    block: ControllerScope<T>.() -> Unit,
) {
    stimulus {
        controller.invoke(block)
    }
}

inline fun Tag.stimulus(block: StimulusTagScope.() -> Unit) {
    StimulusTagScope(this).block()
}

private fun camelToKebab(camel: String): String =
    camel.replace(Regex("([a-z])([A-Z])")) { "${it.groupValues[1]}-${it.groupValues[2].lowercase()}" }

class ControllerScope<T : StimulusController>(val stimulus: Stimulus, val controller: T) : Stimulus by stimulus
