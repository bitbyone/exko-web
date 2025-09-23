package io.exko.sandbox.uikit.components.tooltip

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.tooltip.Tooltip
import io.exko.webawesome.props.Placement
import io.exko.webawesome.props.Trigger.*
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/tooltip")
class TooltipPageController {

    @GetMapping
    fun render() = view(TooltipPage(), ::PlaygroundLayout)
}

@UI
fun TooltipPage() = fragment {
    h1 { +"Tooltip" }

    Basic()
    br
    Placements()
    br
    DistanceAndSkidding()
    br
    Delays()
    br
    Triggers()
    br
    WithoutArrow()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    div {
        Button {
            id = "btn-basic-tip"
            +"Hover me"
        }
        Tooltip(forId = "btn-basic-tip") {
            p { +"This is a basic tooltip attached to the button." }
        }
    }
}

@UI
fun Component.Placements() {
    h2 { +"Placements" }
    div {
        Placement.entries.forEach { placement ->
            div {
                val id = "btn-place-" + placement.realValue
                Button {
                    this.id = id
                    +"Placement: ${placement.realValue}"
                }
                Tooltip(forId = id, placement = placement) {
                    p { +"Placement: ${placement.realValue}" }
                }
            }
        }
    }
}

@UI
fun Component.DistanceAndSkidding() {
    h2 { +"Distance and Skidding" }
    div {
        Button {
            id = "btn-distance-tip"
            +"Distance 16"
        }
        Tooltip(forId = "btn-distance-tip", distance = 16) {
            p { +"Distance set to 16px" }
        }
    }
    br
    div {
        Button {
            id = "btn-skidding-tip"
            +"Skidding 24"
        }
        Tooltip(forId = "btn-skidding-tip", skidding = 24, placement = Placement.right) {
            p { +"Skidding set to 24px" }
        }
    }
    br
    div {
        Button {
            id = "btn-both-tip"
            +"Distance 8, Skidding 12"
        }
        Tooltip(forId = "btn-both-tip", distance = 8, skidding = 12, placement = Placement.bottom) {
            p { +"Distance 8px, Skidding 12px" }
        }
    }
}

@UI
fun Component.Delays() {
    h2 { +"Delays" }
    div {
        Button {
            id = "btn-delays"
            +"Delays"
        }
        Tooltip(forId = "btn-delays", showDelay = 400, hideDelay = 300) {
            p { +"Show delay 400ms, hide delay 300ms" }
        }
    }
}

@UI
fun Component.Triggers() {
    h2 { +"Triggers" }
    // hover focus (default)
    div {
        Button {
            id = "btn-default-triggers"
            +"Hover/Focus (default)"
        }
        Tooltip(forId = "btn-default-triggers") { p { +"Default: hover focus" } }
    }
    br
    // click only
    div {
        Button {
            id = "btn-click-trigger"
            +"Click"
        }
        Tooltip(forId = "btn-click-trigger", trigger = click + focus) { p { +"Triggered by click" } }
    }
    br
    // manual (opened programmatically via open=true for demo)
    div {
        Button {
            id = "btn-manual-trigger"
            +"Manual (forced open)"
        }
        Tooltip(forId = "btn-manual-trigger", trigger = +manual, open = false) {
            p { +"Manual trigger; opened via open=true" }
        }
    }
}

@UI
fun Component.WithoutArrow() {
    h2 { +"Without Arrow" }
    div {
        Button {
            id = "btn-no-arrow-tip"
            +"No Arrow"
        }
        Tooltip(forId = "btn-no-arrow-tip", withoutArrow = true) {
            p { +"Tooltip without arrow" }
        }
    }
}
