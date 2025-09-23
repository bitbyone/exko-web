package io.exko.sandbox.uikit.components.tree

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.icon.Icon
import io.exko.webawesome.components.tree.Tree
import io.exko.webawesome.components.tree.TreeItem
import io.exko.webawesome.props.TreeSelection
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/tree")
class TreePageController {
    @GetMapping
    fun render() = view(TreePage(), ::PlaygroundLayout)
}

@UI
fun TreePage() = fragment {
    h1 { +"Tree" }

    SingleSelection()
    br
    MultipleSelection()
    br
    LeafSelection()
    br
    LazyLoading()
    br
    CustomIcons()
}

@UI
fun Component.SingleSelection() {
    h2 { +"Single selection" }
    Tree(selection = TreeSelection.single) {
        TreeItem {
            +"Fruits"
            TreeItem { +"Apple" }
            TreeItem { +"Banana" }
        }
        TreeItem {
            +"Vegetables"
            TreeItem { +"Carrot" }
            TreeItem { +"Potato" }
        }
    }
}

@UI
fun Component.MultipleSelection() {
    h2 { +"Multiple selection" }
    Tree(selection = TreeSelection.multiple) {
        TreeItem {
            +"Group A"
            TreeItem { +"One" }
            TreeItem { +"Two" }
        }
        TreeItem {
            +"Group B"
            TreeItem { +"Three" }
            TreeItem { +"Four" }
        }
    }
}

@UI
fun Component.LeafSelection() {
    h2 { +"Leaf-only selection" }
    Tree(selection = TreeSelection.leaf) {
        TreeItem {
            +"Parent"
            TreeItem { +"Child 1" }
            TreeItem { +"Child 2" }
        }
    }
}

@UI
fun Component.LazyLoading() {
    h2 { +"Lazy items" }
    Tree(selection = TreeSelection.single) {
        TreeItem(lazy = true) { +"Lazy parent" }
    }
}

@UI
fun Component.CustomIcons() {
    h2 { +"Custom expand/collapse icons" }
    Tree(selection = TreeSelection.single) {
        slotExpandIcon { Icon(name = "chevron-up") }
        slotCollapseIcon { Icon(name = "chevron-down") }
        TreeItem {
            +"Node"
            TreeItem { +"Child" }
        }
    }
}
