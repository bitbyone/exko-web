package io.exko.webawesome.components.tabs

import io.exko.html.*
import io.exko.webawesome.props.ActiveAware
import io.exko.webawesome.props.NameAware
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_TAB_PANEL(consumer: TagConsumer<*>) : HTMLTag(
    "wa-tab-panel",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    NameAware,
    ActiveAware

fun FlowContent.TabPanel(
    classes: String? = null,
    id: String? = null,
    name: String? = null,
    active: Boolean? = null,
    block: WA_TAB_PANEL.() -> Unit = {}
) {
    val panel = WA_TAB_PANEL(consumer)
    panel.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        name?.let { this.name = it }
        active?.let { this.active = it }
    }
}
