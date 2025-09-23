package io.exko.webawesome.components.tree

import io.exko.html.*
import io.exko.webawesome.props.DisabledAware
import io.exko.webawesome.props.LazyAware
import io.exko.webawesome.props.SelectedAware
import io.exko.webawesome.props.tickerAttr
import kotlinx.html.*

class WA_TREE_ITEM(consumer: TagConsumer<*>) : HTMLTag(
    "wa-tree-item",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    DisabledAware, SelectedAware, LazyAware {

    var expanded: Boolean
        get() = tickerAttr[this, "expanded"]
        set(value) { tickerAttr[this, "expanded"] = value }

    // Slots
    fun slotExpandIcon(children: Children) = children.visitWithSlotAttribute("expand-icon")
    fun slotCollapseIcon(children: Children) = children.visitWithSlotAttribute("collapse-icon")
}

fun FlowContent.TreeItem(
    classes: String? = null,
    id: String? = null,
    expanded: Boolean? = null,
    selected: Boolean? = null,
    disabled: Boolean? = null,
    lazy: Boolean? = null,
    block: WA_TREE_ITEM.() -> Unit = {}
) {
    val item = WA_TREE_ITEM(consumer)
    item.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        expanded?.let { this.expanded = it }
        selected?.let { this.selected = it }
        disabled?.let { this.disabled = it }
        lazy?.let { this.lazy = it }
    }
}
