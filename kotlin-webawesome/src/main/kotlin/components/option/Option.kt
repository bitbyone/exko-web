package io.exko.webawesome.components.option

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_OPTION(consumer: TagConsumer<*>) : HTMLTag(
    "wa-option",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    ValueAware, DisabledAware, LabelAware, SelectedAware {

    // Slots
    fun slotStart(children: Children) = children.visitWithSlotAttribute("start")
    fun slotEnd(children: Children) = children.visitWithSlotAttribute("end")
}

fun FlowContent.Option(
    classes: String? = null,
    id: String? = null,
    value: String? = null,
    disabled: Boolean? = null,
    selected: Boolean? = null,
    label: String? = null,
    block: WA_OPTION.() -> Unit = {}
) {
    val option = WA_OPTION(consumer)
    option.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        value?.let { this.value = it }
        disabled?.let { this.disabled = it }
        selected?.let { this.selected = it }
        label?.let { this.label = it }
    }
}
