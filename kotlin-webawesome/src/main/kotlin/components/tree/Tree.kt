package io.exko.webawesome.components.tree

import io.exko.html.*
import io.exko.webawesome.props.TreeSelection
import io.exko.webawesome.props.TreeSelectionAware
import kotlinx.html.*

class WA_TREE(consumer: TagConsumer<*>) : HTMLTag(
    "wa-tree",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, TreeSelectionAware {

    // Slots
    fun slotExpandIcon(children: Children) = children.visitWithSlotAttribute("expand-icon")
    fun slotCollapseIcon(children: Children) = children.visitWithSlotAttribute("collapse-icon")
}

fun FlowContent.Tree(
    classes: String? = null,
    id: String? = null,
    selection: TreeSelection? = null,
    block: WA_TREE.() -> Unit = {}
) {
    val tree = WA_TREE(consumer)
    tree.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        selection?.let { this.selection = it }
    }
}
