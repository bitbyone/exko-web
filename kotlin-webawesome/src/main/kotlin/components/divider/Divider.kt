package io.exko.webawesome.components.divider

import io.exko.html.*
import io.exko.webawesome.props.Orientation
import io.exko.webawesome.props.OrientationAware
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_DIVIDER(consumer: TagConsumer<*>) : HTMLTag(
    "wa-divider",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, OrientationAware

fun FlowContent.Divider(
    classes: String? = null,
    id: String? = null,
    orientation: Orientation? = null,
    block: WA_DIVIDER.() -> Unit = {}
) {
    val divider = WA_DIVIDER(consumer)
    divider.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        orientation?.let { this.orientation = it }
    }
}

