package io.exko.webawesome.components.radio

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_RADIO_GROUP(consumer: TagConsumer<*>) : HTMLTag(
    "wa-radio-group",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    LabelAware, HintAware, NameAware, DisabledAware, OrientationAware, SizeAware, RequiredAware,
    ValueAware, WithLabelAware, WithHintAware {

    // Slots
    fun slotLabel(children: Children) = children.visitWithSlotAttribute("label")
    fun slotHint(children: Children) = children.visitWithSlotAttribute("hint")
}

fun FlowContent.RadioGroup(
    classes: String? = null,
    id: String? = null,
    label: String? = null,
    hint: String? = null,
    name: String? = null,
    disabled: Boolean? = null,
    orientation: Orientation? = null,
    size: Size? = null,
    required: Boolean? = null,
    withLabel: Boolean? = null,
    withHint: Boolean? = null,
    value: String? = null,
    block: WA_RADIO_GROUP.() -> Unit = {}
) {
    val group = WA_RADIO_GROUP(consumer)
    group.visit(block) {
        classes?.let { classes(it) }
        label?.let { this.label = it }
        hint?.let { this.hint = it }
        name?.let { this.name = it }
        disabled?.let { this.disabled = it }
        orientation?.let { this.orientation = it }
        size?.let { this.size = it }
        required?.let { this.required = it }
        withLabel?.let { this.withLabel = it }
        withHint?.let { this.withHint = it }
        value?.let { this.value = it }
    }
}
