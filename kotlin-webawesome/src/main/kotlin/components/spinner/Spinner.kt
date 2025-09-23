package io.exko.webawesome.components.spinner

import io.exko.html.*
import kotlinx.html.*

class WA_SPINNER(consumer: TagConsumer<*>) : HTMLTag(
    "wa-spinner",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade

fun FlowContent.Spinner(
    classes: String? = null,
    id: String? = null,
    block: WA_SPINNER.() -> Unit = {}
) {
    val spinner = WA_SPINNER(consumer)
    spinner.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
    }
}
