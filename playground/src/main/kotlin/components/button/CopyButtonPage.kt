package io.exko.sandbox.uikit.components.button

import io.exko.html.Component
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.CopyButton
import io.exko.webawesome.props.SimplePlacement
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.span
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/copy-button")
class CopyButtonPageController {
    @GetMapping
    fun render() = view(CopyButtonPage(), ::PlaygroundLayout)
}

fun CopyButtonPage() = fragment {
    h1 { +"Copy Button" }

    Basic()
    br
    Placements()
}

fun Component.Basic() {
    h2 { +"Basic" }
    CopyButton(value = "Hello world", copyLabel = "Copy", successLabel = "Copied!", errorLabel = "Error", feedbackDuration = 1500) {
        slotCopyIcon { span { +"📋" } }
        slotSuccessIcon { span { +"✅" } }
        slotErrorIcon { span { +"⛔" } }
    }
}

fun Component.Placements() {
    h2 { +"Tooltip Placements" }
    listOf(SimplePlacement.top, SimplePlacement.right, SimplePlacement.bottom, SimplePlacement.left).forEach { p ->
        CopyButton(value = "Placement: ${'$'}{p.name}", copyLabel = p.name, tooltipPlacement = p)
        br
    }
}
