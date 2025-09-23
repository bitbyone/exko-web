package io.exko.webawesome.components.breadcrumb

import io.exko.html.*
import io.exko.webawesome.props.LabelAware
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.id

class WA_BREADCRUMB(consumer: TagConsumer<*>) : HTMLTag(
    "wa-breadcrumb",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, LabelAware {

    fun slotSeparator(children: Children) = children.visitWithSlotAttribute("separator")
}

fun FlowContent.Breadcrumb(
    classes: String? = null,
    id: String? = null,
    label: String? = null,
    block: WA_BREADCRUMB.() -> Unit = {}
) {
    val breadcrumb = WA_BREADCRUMB(consumer)
    breadcrumb.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        label?.let { this.label = it }
    }
}
