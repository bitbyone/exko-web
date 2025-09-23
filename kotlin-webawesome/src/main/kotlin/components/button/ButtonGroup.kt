package io.exko.webawesome.components.button

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_BUTTON_GROUP(consumer: TagConsumer<*>) : HTMLTag(
    "wa-button-group",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, LabelAware, OrientationAware, VariantAware

fun FlowContent.ButtonGroup(
    classes: String? = null,
    id: String? = null,
    label: String? = null,
    orientation: Orientation? = null,
    variant: Variant? = null,
    block: WA_BUTTON_GROUP.() -> Unit = {}
) {
    val group = WA_BUTTON_GROUP(consumer)
    group.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        label?.let { this.label = it }
        orientation?.let { this.orientation = it }
        variant?.let { this.variant = it }
    }
}

