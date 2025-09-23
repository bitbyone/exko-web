package io.exko.sandbox.uikit.components.dialog

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.dialog.Dialog
import io.exko.webawesome.props.Appearance
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/dialog")
class DialogPageController {
    @GetMapping
    fun render() = view(DialogPage(), ::PlaygroundLayout)
}

@UI
fun DialogPage() = fragment {
    h1 { +"Dialog" }

    Basic()
    br
    Slotted()
    br
    Headerless()
    br
    LightDismiss()
}

@UI
fun Component.Basic() {
    h2 { +"Basic (open)" }
    Dialog(open = true, label = "Basic Dialog") {
        +"This is the dialog content."
        slotFooter {
            Button { +"Ok" }
            Button(appearance = Appearance.plain) { +"Cancel" }
        }
    }
}

@UI
fun Component.Slotted() {
    h2 { +"Slots: label, header-actions, footer" }
    Dialog(open = true) {
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
fun Component.Headerless() {
    h2 { +"Without header" }
    Dialog(open = true, withoutHeader = false) {
        +"Header is disabled; no close button."
        slotFooter { Button { +"Close" } }
    }
}

@UI
fun Component.LightDismiss() {
    h2 { +"Light dismiss (click outside to close)" }
    Dialog(open = true, label = "Light Dismiss", lightDismiss = true) {
        +"Try clicking outside the dialog to close it."
    }
}
