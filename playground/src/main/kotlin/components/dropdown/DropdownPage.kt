package io.exko.sandbox.uikit.components.dropdown

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.divider.Divider
import io.exko.webawesome.components.dropdown.Dropdown
import io.exko.webawesome.components.dropdown.DropdownItem
import io.exko.webawesome.components.dropdown.attr.DropdownItemType
import io.exko.webawesome.components.dropdown.attr.DropdownItemVariant
import io.exko.webawesome.props.Placement
import io.exko.webawesome.props.Size
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.span
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/dropdown")
class DropdownPageController {

    @GetMapping
    fun render() = view(DropdownPage(), ::PlaygroundLayout)
}

@UI
fun DropdownPage() = fragment {
    h1 { +"Dropdown" }

    Basic()
    br
    WithIconsAndSeparators()
    br
    CheckboxItems()
    br
    Submenu()
    br
    Sizes()
    br
    Placements()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    Dropdown {
        slotTrigger { Button(withCaret = true) { +"Open" } }
        DropdownItem { +"Item 1" }
        DropdownItem { +"Item 2" }
        DropdownItem(variant = DropdownItemVariant.danger) { +"Danger" }
    }
}

@UI
fun Component.WithIconsAndSeparators() {
    h2 { +"With Icons and Separators" }
    Dropdown {
        slotTrigger { Button(withCaret = true) { +"Dropdown" } }

        DropdownItem {
            slotIcon { span { +"✂️" } } // scissors icon placeholder
            +"Cut"
        }
        DropdownItem {
            slotIcon { span { +"📋" } } // copy icon placeholder
            +"Copy"
        }
        DropdownItem {
            slotIcon { span { +"📄" } } // paste icon placeholder
            +"Paste"
        }

        Divider()

        DropdownItem {
            +"Show images"
            slotSubmenu {
                DropdownItem(value = "show-all-images") { +"Show All Images" }
            }
            slotSubmenu {
                DropdownItem(value = "show-thumbnails") { +"Show Thumbnails" }
            }
        }

        Divider()

        DropdownItem(type = DropdownItemType.checkbox, checked = true) { +"Emoji Shortcuts" }
        DropdownItem(type = DropdownItemType.checkbox, checked = true) { +"Word Wrap" }

        Divider()

        DropdownItem(variant = DropdownItemVariant.danger) {
            slotIcon { span { +"🗑️" } } // trash icon placeholder
            +"Delete"
        }
    }
}

@UI
fun Component.CheckboxItems() {
    h2 { +"Checkbox items" }
    Dropdown {
        slotTrigger { Button(withCaret = true) { +"Options" } }
        DropdownItem(type = DropdownItemType.checkbox, checked = true, value = "a") { +"Enable A" }
        DropdownItem(type = DropdownItemType.checkbox, checked = false, value = "b") { +"Enable B" }
        DropdownItem { +"Regular item" }
    }
}

@UI
fun Component.Submenu() {
    h2 { +"Submenu" }
    Dropdown {
        slotTrigger { Button(withCaret = true) { +"More" } }
        DropdownItem {
            +"Actions"
            slotSubmenu {
                DropdownItem { +"Edit" }
            }
            slotSubmenu {
                DropdownItem(variant = DropdownItemVariant.danger) { +"Delete" }
            }
            slotSubmenu {
                DropdownItem { +"Duplicate" }
            }
        }
        DropdownItem { +"Settings" }
    }
}

@UI
fun Component.Sizes() {
    h2 { +"Sizes" }
    Size.entries.forEach { size ->
        Dropdown(size = size) {
            slotTrigger { Button { +"Size: ${size.name}" } }
            DropdownItem { +"Item" }
        }
    }
}

@UI
fun Component.Placements() {
    h2 { +"Placements" }
    listOf(
        Placement.top,
        Placement.bottom,
        Placement.right,
        Placement.left,
        Placement.bottomStart,
        Placement.bottomEnd,
    ).forEach { placement ->
        Dropdown(placement = placement) {
            slotTrigger { Button { +placement.realValue } }
            DropdownItem { +"First" }
            DropdownItem { +"Second" }
        }
    }
}
