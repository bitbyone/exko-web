package io.exko.webawesome.components.slider

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.id

class WA_SLIDER(consumer: TagConsumer<*>) : HTMLTag(
    "wa-slider",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    LabelAware, HintAware, WithLabelAware, WithHintAware, FormAware,
    NameAware, SizeAware, DisabledAware, RequiredAware, ReadonlyAware, OrientationAware,
    AutofocusAware {

    var range: Boolean
        get() = tickerAttr[this, "range"]
        set(value) { tickerAttr[this, "range"] = value }

    var value: Int
        get() = intAttr[this, "value"]
        set(value) { intAttr[this, "value"] = value }

    var min: Int
        get() = intAttr[this, "min"]
        set(value) { intAttr[this, "min"] = value }

    var max: Int
        get() = intAttr[this, "max"]
        set(value) { intAttr[this, "max"] = value }

    var minValue: Int
        get() = intAttr[this, "min-value"]
        set(value) { intAttr[this, "min-value"] = value }

    var maxValue: Int
        get() = intAttr[this, "max-value"]
        set(value) { intAttr[this, "max-value"] = value }

    var step: Int
        get() = intAttr[this, "step"]
        set(value) { intAttr[this, "step"] = value }

    var withMarkers: Boolean
        get() = tickerAttr[this, "with-markers"]
        set(value) { tickerAttr[this, "with-markers"] = value }

    var withTooltip: Boolean
        get() = tickerAttr[this, "with-tooltip"]
        set(value) { tickerAttr[this, "with-tooltip"] = value }

    var tooltipPlacement: SimplePlacement
        get() = simplePlacementAttr[this, "tooltip-placement"]
        set(value) { simplePlacementAttr[this, "tooltip-placement"] = value }

    var tooltipDistance: Int
        get() = intAttr[this, "tooltip-distance"]
        set(value) { intAttr[this, "tooltip-distance"] = value }

    var indicatorOffset: Int
        get() = intAttr[this, "indicator-offset"]
        set(value) { intAttr[this, "indicator-offset"] = value }

    // Slots
    fun slotLabel(children: Children) = children.visitWithSlotAttribute("label")
    fun slotHint(children: Children) = children.visitWithSlotAttribute("hint")
    fun slotReference(children: Children) = children.visitWithSlotAttribute("reference")
}

fun FlowContent.Slider(
    classes: String? = null,
    id: String? = null,
    label: String? = null,
    hint: String? = null,
    withLabel: Boolean? = null,
    withHint: Boolean? = null,
    name: String? = null,
    size: Size? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    readonly: Boolean? = null,
    orientation: Orientation? = null,
    range: Boolean? = null,
    value: Int? = null,
    min: Int? = null,
    max: Int? = null,
    minValue: Int? = null,
    maxValue: Int? = null,
    step: Int? = null,
    withMarkers: Boolean? = null,
    withTooltip: Boolean? = null,
    tooltipPlacement: SimplePlacement? = null,
    tooltipDistance: Int? = null,
    form: String? = null,
    autofocus: Boolean? = null,
    indicatorOffset: Int? = null,
    block: WA_SLIDER.() -> Unit = {}
) {
    val slider = WA_SLIDER(consumer)
    slider.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        label?.let { this.label = it }
        hint?.let { this.hint = it }
        withLabel?.let { this.withLabel = it }
        withHint?.let { this.withHint = it }
        name?.let { this.name = it }
        size?.let { this.size = it }
        disabled?.let { this.disabled = it }
        required?.let { this.required = it }
        readonly?.let { this.readonly = it }
        orientation?.let { this.orientation = it }
        range?.let { this.range = it }
        value?.let { this.value = it }
        min?.let { this.min = it }
        max?.let { this.max = it }
        minValue?.let { this.minValue = it }
        maxValue?.let { this.maxValue = it }
        step?.let { this.step = it }
        withMarkers?.let { this.withMarkers = it }
        withTooltip?.let { this.withTooltip = it }
        tooltipPlacement?.let { this.tooltipPlacement = it }
        tooltipDistance?.let { this.tooltipDistance = it }
        form?.let { this.form = it }
        autofocus?.let { this.autofocus = it }
        indicatorOffset?.let { this.indicatorOffset = it }
    }
}
