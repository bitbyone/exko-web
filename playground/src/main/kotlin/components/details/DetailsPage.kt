package io.exko.sandbox.uikit.components.details

import io.exko.html.Component
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.details.Details
import io.exko.webawesome.props.Appearance
import io.exko.webawesome.props.Appearance.*
import io.exko.webawesome.props.IconPlacement
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/details")
class DetailsPageController {

    @GetMapping
    fun render() = view(DetailsPage(), ::PlaygroundLayout)
}

fun DetailsPage() = fragment {
    h1 { +"Details" }

    Basic()
    br
    SummarySlot()
    br
    Grouped()
    br
    Appearances()
    br
    Disabled()
    br
    IconPositions()
    br
    CustomIcons()
}

fun Component.Basic() {
    h2 { +"Basic" }
    Details(summary = "Toggle me") {
        p { +"This is the content inside the details component." }
    }
}

fun Component.SummarySlot() {
    h2 { +"Summary Slot (HTML in header)" }
    Details {
        slotSummary {
            h3 { +"Summary with " }
        }
        p { +"Content displayed when expanded." }
    }
}

fun Component.Grouped() {
    h2 { +"Grouped (same name)" }
    div {
        Details(summary = "Group A - Item 1", name = "groupA") { p { +"Only one in group A stays open." } }
        Details(summary = "Group A - Item 2", name = "groupA", open = true) {
            p { +"Opening this will close others in group A." }
        }
        Details(summary = "Group A - Item 3", name = "groupA") { p { +"Another item in the same group." } }
    }
}

fun Component.Appearances() {
    h2 { +"Appearances" }
    Appearance.entries.forEach { app ->
        Details(summary = "Appearance: ${app.name}", appearance = app) {
            p { +"This details uses ${app.name} appearance." }
        }
    }
    Details(summary = "Appearance: filled + outlined", appearance = filledOutlined) {
        p { +"This details uses filled + outlined appearance." }
    }
}

fun Component.Disabled() {
    h2 { +"Disabled" }
    Details(summary = "Disabled details", disabled = true) {
        p { +"You shouldn't be able to toggle this." }
    }
}

fun Component.IconPositions() {
    h2 { +"Icon Position" }
    Details(summary = "Icon at start", iconPlacement = IconPlacement.start) { p { +"Icon is rendered at the start." } }
    Details(summary = "Icon at end (default)", iconPlacement = IconPlacement.end) {
        p { +"Icon is rendered at the end." }
    }
}

fun Component.CustomIcons() {
    h2 { +"Custom expand/collapse icons" }
    Details(summary = "Custom icons") {
        slotCollapseIcon { span { +"➖" } }
        slotExpandIcon { span { +"➕" } }
        p { +"Using custom expand and collapse icons via slots." }
    }
}
