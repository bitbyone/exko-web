package io.exko.webawesome.components.tooltip

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_TOOLTIP(consumer: TagConsumer<*>) : HTMLTag(
    "wa-tooltip",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    PlacementAware,
    DisabledAware,
    DistanceAware,
    OpenAware,
    SkiddingAware,
    ShowDelayAware,
    HideDelayAware,
    TriggerAware,
    ForAware,
    WithoutArrowAware

fun FlowContent.Tooltip(
    classes: String? = null,
    id: String? = null,
    placement: Placement? = null,
    disabled: Boolean? = null,
    distance: Int? = null,
    open: Boolean? = null,
    skidding: Int? = null,
    showDelay: Int? = null,
    hideDelay: Int? = null,
    trigger: Set<Trigger>? = null,
    forId: String? = null,
    withoutArrow: Boolean? = null,
    block: WA_TOOLTIP.() -> Unit = {}
) {
    val tooltip = WA_TOOLTIP(consumer)
    tooltip.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        placement?.let { this.placement = it }
        disabled?.let { this.disabled = it }
        distance?.let { this.distance = it }
        open?.let { this.open = it }
        skidding?.let { this.skidding = it }
        showDelay?.let { this.showDelay = it }
        hideDelay?.let { this.hideDelay = it }
        trigger?.let { this.trigger = it }
        forId?.let { this.forId = it }
        withoutArrow?.let { this.withoutArrow = it }
    }
}
