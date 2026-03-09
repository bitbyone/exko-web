package io.exko.stimulus

import io.exko.html.Component
import kotlinx.html.Tag

class ControllerScope(private val name: String) {

    private val controllers = mutableListOf<String>()
    private val targets = mutableListOf<Pair<String, String>>()
    private val values = mutableListOf<Pair<String, String>>()
    private val actions = mutableListOf<String>()
    private val outlets = mutableListOf<Pair<String, String>>()

    fun controller() {
        controllers.add(name)
    }

    fun target(targetName: String) {
        targets.add("data-$name-target" to targetName)
    }

    fun value(
        valueName: String,
        v: String,
    ) {
        val kebabName = camelToKebab(valueName)
        values.add("data-$name-$kebabName-value" to v)
    }

    fun action(
        method: String,
        on: String = "click",
    ) {
        actions.add("$on->$name#$method")
    }

    fun outlet(
        outletController: StimulusController,
        selector: String,
    ) {
        outlets.add("data-$name-${outletController.name}-outlet" to selector)
    }

    fun outlet(
        outletName: String,
        selector: String,
    ) {
        outlets.add("data-$name-$outletName-outlet" to selector)
    }

    internal fun apply(tag: Tag) {
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

class StimulusScope(private val tag: Tag) {

    operator fun StimulusController.invoke(block: ControllerScope.() -> Unit) {
        val scope = ControllerScope(name)
        scope.block()
        scope.apply(tag)
    }
}

fun Component.stimulus(block: StimulusScope.() -> Unit) {
    StimulusScope(this).block()
}

fun Component.stimulus(
    controller: StimulusController,
    block: ControllerScope.() -> Unit,
) {
    stimulus { controller(block) }
}

fun Tag.stimulus(block: StimulusScope.() -> Unit) {
    StimulusScope(this).block()
}

fun Tag.stimulus(
    controller: StimulusController,
    block: ControllerScope.() -> Unit,
) {
    stimulus { controller(block) }
}

private fun camelToKebab(camel: String): String =
    camel.replace(Regex("([a-z])([A-Z])")) { "${it.groupValues[1]}-${it.groupValues[2].lowercase()}" }
