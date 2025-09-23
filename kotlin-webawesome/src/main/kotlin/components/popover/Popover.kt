package io.exko.webawesome.components.popover

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_POPOVER(consumer: TagConsumer<*>) : HTMLTag(
    "wa-popover",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    PlacementAware, OpenAware, DistanceAware, SkiddingAware, ForAware, WithoutArrowAware

fun FlowContent.Popover(
    classes: String? = null,
    id: String? = null,
    placement: Placement? = null,
    open: Boolean? = null,
    distance: Int? = null,
    skidding: Int? = null,
    forId: String? = null,
    withoutArrow: Boolean? = null,
    block: WA_POPOVER.() -> Unit = {}
) {
    val popover = WA_POPOVER(consumer)
    popover.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        placement?.let { this.placement = it }
        open?.let { this.open = it }
        distance?.let { this.distance = it }
        skidding?.let { this.skidding = it }
        forId?.let { this.forId = it }
        withoutArrow?.let { this.withoutArrow = it }
    }
}
