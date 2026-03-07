package io.exko.webawesome.components.toast

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.*

@HtmlTag
class WA_TOAST(consumer: TagConsumer<*>) :
    HTMLTag(
        "wa-toast",
        consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
        emptyMap(),
        inlineTag = false,
        emptyTag = false,
    ),
    FlowContent,
    WebComponent,
    CoreAttributeGroupFacade,
    ToastPlacementAware

@UI
@HtmlTagMarker
fun FlowContent.Toast(
    classes: String? = null,
    id: String? = null,
    placement: ToastPlacement? = null,
    block: WA_TOAST.() -> Unit = {},
) {
    val toast = WA_TOAST(consumer)
    toast.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        placement?.let { this.placement = it }
    }
}
