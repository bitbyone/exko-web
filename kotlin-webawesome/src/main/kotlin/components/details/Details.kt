package io.exko.webawesome.components.details

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.*

@HtmlTag
class WA_DETAILS(consumer: TagConsumer<*>) :
    HTMLTag(
        "wa-details",
        consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
        emptyMap(),
        inlineTag = false,
        emptyTag = false,
    ),
    FlowContent,
    WebComponent,
    CoreAttributeGroupFacade,
    OpenAware,
    SummaryAware,
    NameAware,
    DisabledAware,
    AppearanceAware,
    IconPlacementAware {

    fun slotSummary(children: Children) = children.visitWithSlotAttribute("summary")
    fun slotExpandIcon(children: Children) = children.visitWithSlotAttribute("expand-icon")
    fun slotCollapseIcon(children: Children) = children.visitWithSlotAttribute("collapse-icon")
}

@UI
@HtmlTagMarker
fun FlowContent.Details(
    classes: String? = null,
    id: String? = null,
    open: Boolean? = null,
    summary: String? = null,
    name: String? = null,
    disabled: Boolean? = null,
    appearance: Appearance? = null,
    iconPlacement: IconPlacement? = null,
    block: WA_DETAILS.() -> Unit = {},
) {
    val details = WA_DETAILS(consumer)
    details.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        open?.let { this.open = it }
        summary?.let { this.summary = it }
        name?.let { this.name = it }
        disabled?.let { this.disabled = it }
        appearance?.let { this.appearance = it }
        iconPlacement?.let { this.iconPlacement = it }
    }
}
