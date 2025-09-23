package io.exko.webawesome.components.tabs

import io.exko.html.*
import io.exko.webawesome.props.DisabledAware
import io.exko.webawesome.props.PanelAware
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer

class WA_TAB(consumer: TagConsumer<*>) : HTMLTag(
    "wa-tab",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    PanelAware,
    DisabledAware

fun FlowContent.Tab(
    classes: String? = null,
    id: String? = null,
    panel: String? = null,
    disabled: Boolean? = null,
    block: WA_TAB.() -> Unit = {}
) {
    val tab = WA_TAB(consumer)
    tab.visit(block) {
        classes?.let { classes(it) }
        panel?.let { this.panel = it }
        disabled?.let { this.disabled = it }
    }
}
