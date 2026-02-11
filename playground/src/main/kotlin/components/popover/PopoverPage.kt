package io.exko.sandbox.uikit.components.popover

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.popover.Popover
import io.exko.webawesome.props.Placement.*
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/popover")
class PopoverPageController {

    @GetMapping
    fun render() = view(PopoverPage(), ::PlaygroundLayout)
}

@UI
fun PopoverPage() = fragment {
    h1 { +"Popover" }

    Basic()
    br
    Placements()
    br
    DistanceAndSkidding()
    br
    WithoutArrow()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    div {
        Button {
            id = "btn-basic"
            +"Click me !!!"
        }
        Popover(id = "pop1", forId = "btn-basic") {
            p { +"This is a basic popover anchored to a button. amazing... right?" }
        }
    }
}

@UI
fun Component.Placements() {
    h2 { +"Placements" }
    // Showcase multiple placements around different buttons
    div("wa-stack") {
        div {
            Button {
                id = "btn-top"
                +"Top"
            }
            Popover(forId = "btn-top", placement = top) { p { +"Top" } }
        }
        div {
            Button {
                id = "btn-top-start"
                +"Top Start"
            }
            Popover(forId = "btn-top-start", placement = topStart) { p { +"Top Start" } }
        }
        div {
            Button {
                id = "btn-top-end"
                +"Top End"
            }
            Popover(forId = "btn-top-end", placement = topEnd) { p { +"Top End" } }
        }
        div {
            Button {
                id = "btn-right"
                +"Right"
            }
            Popover(forId = "btn-right", placement = right) { p { +"Right" } }
        }
        div {
            Button {
                id = "btn-right-start"
                +"Right Start"
            }
            Popover(forId = "btn-right-start", placement = rightStart) { p { +"Right Start" } }
        }
        div {
            Button {
                id = "btn-right-end"
                +"Right End"
            }
            Popover(forId = "btn-right-end", placement = rightEnd) { p { +"Right End" } }
        }
        div {
            Button {
                id = "btn-bottom"
                +"Bottom"
            }
            Popover(forId = "btn-bottom", placement = bottom) { p { +"Bottom" } }
        }
        div {
            Button {
                id = "btn-bottom-start"
                +"Bottom Start"
            }
            Popover(forId = "btn-bottom-start", placement = bottomStart) { p { +"Bottom Start" } }
        }
        div {
            Button {
                id = "btn-bottom-end"
                +"Bottom End"
            }
            Popover(forId = "btn-bottom-end", placement = bottomEnd) { p { +"Bottom End" } }
        }
        div {
            Button {
                id = "btn-left"
                +"Left"
            }
            Popover(forId = "btn-left", placement = left) { p { +"Left" } }
        }
        div {
            Button {
                id = "btn-left-start"
                +"Left Start"
            }
            Popover(forId = "btn-left-start", placement = leftStart) { p { +"Left Start" } }
        }
        div {
            Button {
                id = "btn-left-end"
                +"Left End"
            }
            Popover(forId = "btn-left-end", placement = leftEnd) { p { +"Left End" } }
        }
    }
}

@UI
fun Component.DistanceAndSkidding() {
    h2 { +"Distance and Skidding" }
    div {
        Button {
            id = "btn-distance"
            +"Distance 16"
        }
        Popover(forId = "btn-distance", placement = top, distance = 16) {
            p { +"Distance 16px from anchor" }
        }
    }
    br
    div {
        Button {
            id = "btn-skidding"
            +"Skidding 24"
        }
        Popover(forId = "btn-skidding", placement = right, skidding = 24) {
            p { +"Skidding 24px along anchor" }
        }
    }
    br
    div {
        Button {
            id = "btn-both"
            +"Distance 12, Skidding 12"
        }
        Popover(forId = "btn-both", placement = bottom, distance = 12, skidding = 12) {
            p { +"Both distance and skidding set to 12px" }
        }
    }
}

@UI
fun Component.WithoutArrow() {
    h2 { +"Without Arrow" }
    div {
        Button {
            id = "btn-no-arrow"
            +"No Arrow"
        }
        Popover(forId = "btn-no-arrow", placement = top, withoutArrow = true) {
            p { +"Popover without arrow" }
        }
    }
}
