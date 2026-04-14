package io.exko.webawesome.components.card

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.*

@HtmlTag
class WA_CARD(consumer: TagConsumer<*>) :
    HTMLTag(
        "wa-card",
        consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
        emptyMap(),
        inlineTag = false,
        emptyTag = false,
    ),
    FlowContent,
    WebComponent,
    CoreAttributeGroupFacade,
    AppearanceAware,
    WithHeaderAware,
    WithMediaAware,
    WithFooterAware,
    OrientationAware {

    fun slotHeader(children: Children) = children.visitWithSlotAttribute("header")
    fun slotMedia(children: Children) = children.visitWithSlotAttribute("media")
    fun slotFooter(children: Children) = children.visitWithSlotAttribute("footer")
    fun slotActions(children: Children) = children.visitWithSlotAttribute("actions")
    fun slotHeaderActions(children: Children) = children.visitWithSlotAttribute("header-actions")
    fun slotFooterActions(children: Children) = children.visitWithSlotAttribute("footer-actions")
}

@UI
fun FlowContent.Card(
    classes: String? = null,
    id: String? = null,
    appearance: Appearance? = null,
    withHeader: Boolean? = null,
    withMedia: Boolean? = null,
    withFooter: Boolean? = null,
    orientation: Orientation? = null,
    block: WA_CARD.() -> Unit = {},
) {
    val card = WA_CARD(consumer)
    card.visit(block) {
        classes?.let { classes(classes) }
        id?.let { this.id = it }
        appearance?.let { this.appearance = it }
        withHeader?.let { this.withHeader = it }
        withMedia?.let { this.withMedia = it }
        withFooter?.let { this.withFooter = it }
        orientation?.let { this.orientation = it }
    }
}
