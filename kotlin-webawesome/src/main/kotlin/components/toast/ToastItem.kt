package io.exko.webawesome.components.toast

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.*

@HtmlTag
class WA_TOAST_ITEM(consumer: TagConsumer<*>) :
    HTMLTag(
        "wa-toast-item",
        consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
        emptyMap(),
        inlineTag = false,
        emptyTag = false,
    ),
    FlowContent,
    WebComponent,
    CoreAttributeGroupFacade,
    VariantAware,
    SizeAware,
    DurationAware {

    fun slotIcon(children: Children) = children.visitWithSlotAttribute("icon")
}

@UI
@HtmlTagMarker
fun FlowContent.ToastItem(
    classes: String? = null,
    id: String? = null,
    variant: Variant? = null,
    size: Size? = null,
    duration: Int? = null,
    block: WA_TOAST_ITEM.() -> Unit = {},
) {
    val toastItem = WA_TOAST_ITEM(consumer)
    toastItem.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        variant?.let { this.variant = it }
        size?.let { this.size = it }
        duration?.let { this.duration = it }
    }
}
