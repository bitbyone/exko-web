package io.exko.sandbox.uikit.components.drawer

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.drawer.Drawer
import io.exko.webawesome.props.Appearance
import io.exko.webawesome.props.DrawerPlacement
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/drawer")
class DrawerPageController {
    @GetMapping
    fun render() = view(DrawerPage(), ::PlaygroundLayout)
}

@UI
fun DrawerPage() = fragment {
    h1 { +"Drawer" }

    Basic()
    br
    Slotted()
    br
    Placements()
    br
    Headerless()
    br
    LightDismiss()
}

@UI
fun Component.Basic() {
    h2 { +"Basic (open)" }
    Drawer(open = true, label = "Basic Drawer") {
        +"This is the drawer content."
        slotFooter {
            Button { +"Ok" }
            Button(appearance = Appearance.plain) { +"Cancel" }
        }
    }
}

@UI
fun Component.Slotted() {
    h2 { +"Slots: label, header-actions, footer" }
    Drawer(open = true) {
        slotLabel { +"Slotted Label" }
        +"Content goes here."
        slotHeaderActions {
            Button(appearance = Appearance.plain) { +"Action" }
        }
        slotFooter {
            Button { +"Accept" }
        }
    }
}

@UI
fun Component.Placements() {
    h2 { +"Placements" }
    Drawer(open = true, label = "Top", placement = DrawerPlacement.top) { +"Top placement" }
    Drawer(open = true, label = "End", placement = DrawerPlacement.end) { +"End placement" }
    Drawer(open = true, label = "Bottom", placement = DrawerPlacement.bottom) { +"Bottom placement" }
    Drawer(open = true, label = "Start", placement = DrawerPlacement.start) { +"Start placement" }
}

@UI
fun Component.Headerless() {
    h2 { +"Without header" }
    Drawer(open = true, withoutHeader = true) {
        +"Header is disabled; no close button."
        slotFooter { Button { +"Close" } }
    }
}

@UI
fun Component.LightDismiss() {
    h2 { +"Light dismiss (click outside to close)" }
    Drawer(open = true, label = "Light Dismiss", lightDismiss = true) {
        +"Try clicking outside the drawer to close it."
    }
}
