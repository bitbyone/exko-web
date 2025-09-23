package io.exko.webawesome.components.dialog

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
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.id

class WA_DIALOG(consumer: TagConsumer<*>) : HTMLTag(
    "wa-dialog",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    LabelAware, OpenAware, WithoutHeaderAware, LightDismissAware {

    // Slots
    fun slotLabel(children: Children) = children.visitWithSlotAttribute("label")
    fun slotHeaderActions(children: Children) = children.visitWithSlotAttribute("header-actions")
    fun slotFooter(children: Children) = children.visitWithSlotAttribute("footer")
}

fun FlowContent.Dialog(
    classes: String? = null,
    id: String? = null,
    open: Boolean? = null,
    label: String? = null,
    withoutHeader: Boolean? = null,
    lightDismiss: Boolean? = null,
    block: WA_DIALOG.() -> Unit = {}
) {
    val dialog = WA_DIALOG(consumer)
    dialog.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        open?.let { this.open = it }
        label?.let { this.label = it }
        withoutHeader?.let { this.withoutHeader = it }
        lightDismiss?.let { this.lightDismiss = it }
    }
}
