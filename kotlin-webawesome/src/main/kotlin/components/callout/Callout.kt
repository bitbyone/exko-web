package io.exko.webawesome.components.callout

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_CALLOUT(consumer: TagConsumer<*>) : HTMLTag(
    "wa-callout",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, AppearanceAware, SizeAware, VariantAware {

    fun slotIcon(children: Children) = children.visitWithSlotAttribute("icon")
}

fun FlowContent.Callout(
    classes: String? = null,
    id: String? = null,
    appearance: Set<Appearance>? = null,
    size: Size? = null,
    variant: Variant? = null,
    block: WA_CALLOUT.() -> Unit = {}
) {
    val callout = WA_CALLOUT(consumer)
    callout.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        appearance?.let { this.appearance = it }
        size?.let { this.size = it }
        variant?.let { this.variant = it }
    }
}
