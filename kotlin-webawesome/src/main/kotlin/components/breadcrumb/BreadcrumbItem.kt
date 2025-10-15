package io.exko.webawesome.components.breadcrumb

import io.exko.html.*
import io.exko.webawesome.props.HrefAware
import io.exko.webawesome.props.RelAware
import io.exko.webawesome.props.Target
import io.exko.webawesome.props.TargetAware
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.id

class WA_BREADCRUMB_ITEM(consumer: TagConsumer<*>) : HTMLTag(
    "wa-breadcrumb-item",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, HrefAware, TargetAware, RelAware {

    fun slotStart(children: Children) = children.visitWithSlotAttribute("start")
    fun slotEnd(children: Children) = children.visitWithSlotAttribute("end")
    fun slotSeparator(children: Children) = children.visitWithSlotAttribute("separator")
}

fun FlowContent.BreadcrumbItem(
    classes: String? = null,
    id: String? = null,
    href: String? = null,
    target: Target? = null,
    rel: String? = null,
    block: WA_BREADCRUMB_ITEM.() -> Unit = {}
) {
    val item = WA_BREADCRUMB_ITEM(consumer)
    item.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        href?.let { this.href = it }
        target?.let { this.target = it }
        rel?.let { this.rel = it }
    }
}
