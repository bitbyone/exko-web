package io.exko.webawesome.components.drawer

import io.exko.html.Children
import io.exko.html.WebComponent
import io.exko.html.WebComponentsTagConsumer
import io.exko.html.classes
import io.exko.html.orNewIfNotAlready
import io.exko.html.visit
import io.exko.html.visitWithSlotAttribute
import io.exko.webawesome.props.LabelAware
import io.exko.webawesome.props.LightDismissAware
import io.exko.webawesome.props.OpenAware
import io.exko.webawesome.props.WithoutHeaderAware
import io.exko.webawesome.props.DrawerPlacement
import io.exko.webawesome.props.DrawerPlacementAware
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.id

class WA_DRAWER(consumer: TagConsumer<*>) : HTMLTag(
    "wa-drawer",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    LabelAware, OpenAware, WithoutHeaderAware, LightDismissAware, DrawerPlacementAware {

    // Slots
    fun slotLabel(children: Children) = children.visitWithSlotAttribute("label")
    fun slotHeaderActions(children: Children) = children.visitWithSlotAttribute("header-actions")
    fun slotFooter(children: Children) = children.visitWithSlotAttribute("footer")
}

fun FlowContent.Drawer(
    classes: String? = null,
    id: String? = null,
    open: Boolean? = null,
    label: String? = null,
    placement: DrawerPlacement? = null,
    withoutHeader: Boolean? = null,
    lightDismiss: Boolean? = null,
    block: WA_DRAWER.() -> Unit = {}
) {
    val drawer = WA_DRAWER(consumer)
    drawer.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        open?.let { this.open = it }
        label?.let { this.label = it }
        placement?.let { this.placement = it }
        withoutHeader?.let { this.withoutHeader = it }
        lightDismiss?.let { this.lightDismiss = it }
    }
}
