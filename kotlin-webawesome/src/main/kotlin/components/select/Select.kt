package io.exko.webawesome.components.select

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_SELECT(consumer: TagConsumer<*>) : HTMLTag(
    "wa-select",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    SizeAware, PillAware, LabelAware, HintAware, WithLabelAware, WithHintAware,
    NameAware, RequiredAware, PlaceholderAware, DisabledAware, FormAware, SimpleAppearanceAware, SelectPlacementAware,
    ValueAware, WithClearAware, MultipleAware, OpenAware {

    var maxOptionsVisible: Int
        get() = intAttr[this, "max-options-visible"]
        set(value) { intAttr[this, "max-options-visible"] = value }

    // Slots
    fun slotLabel(children: Children) = children.visitWithSlotAttribute("label")
    fun slotStart(children: Children) = children.visitWithSlotAttribute("start")
    fun slotEnd(children: Children) = children.visitWithSlotAttribute("end")
    fun slotClearIcon(children: Children) = children.visitWithSlotAttribute("clear-icon")
    fun slotExpandIcon(children: Children) = children.visitWithSlotAttribute("expand-icon")
    fun slotHint(children: Children) = children.visitWithSlotAttribute("hint")
}

fun FlowContent.Select(
    classes: String? = null,
    id: String? = null,
    size: Size? = null,
    pill: Boolean? = null,
    label: String? = null,
    hint: String? = null,
    withLabel: Boolean? = null,
    withHint: Boolean? = null,
    name: String? = null,
    required: Boolean? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    appearance: SimpleAppearance? = null,
    multiple: Boolean? = null,
    withClear: Boolean? = null,
    open: Boolean? = null,
    placement: SelectPlacement? = null,
    form: String? = null,
    value: String? = null,
    maxOptionsVisible: Int? = null,
    block: WA_SELECT.() -> Unit = {}
) {
    val select = WA_SELECT(consumer)
    select.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        size?.let { this.size = it }
        pill?.let { this.pill = it }
        label?.let { this.label = it }
        hint?.let { this.hint = it }
        withLabel?.let { this.withLabel = it }
        withHint?.let { this.withHint = it }
        name?.let { this.name = it }
        required?.let { this.required = it }
        placeholder?.let { this.placeholder = it }
        disabled?.let { this.disabled = it }
        appearance?.let { this.appearance = it }
        multiple?.let { this.multiple = it }
        withClear?.let { this.withClear = it }
        open?.let { this.open = it }
        placement?.let { this.placement = it }
        form?.let { this.form = it }
        value?.let { this.value = it }
        maxOptionsVisible?.let { this.maxOptionsVisible = it }
    }
}
