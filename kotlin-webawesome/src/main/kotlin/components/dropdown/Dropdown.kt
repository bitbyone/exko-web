package io.exko.webawesome.components.dropdown

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_DROPDOWN(consumer: TagConsumer<*>) : HTMLTag(
    "wa-dropdown",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, OpenAware, SizeAware, PlacementAware, DistanceAware, SkiddingAware {

    fun slotTrigger(children: Children) = children.visitWithSlotAttribute("trigger")
}

fun FlowContent.Dropdown(
    classes: String? = null,
    id: String? = null,
    open: Boolean? = null,
    size: Size? = null,
    placement: Placement? = null,
    distance: Int? = null,
    skidding: Int? = null,
    block: WA_DROPDOWN.() -> Unit = {}
) {
    val dropdown = WA_DROPDOWN(consumer)
    dropdown.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        open?.let { this.open = it }
        size?.let { this.size = it }
        placement?.let { this.placement = it }
        distance?.let { this.distance = it }
        skidding?.let { this.skidding = it }
    }
}

