package io.exko.sandbox.uikit.components.select

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.divider.Divider
import io.exko.webawesome.components.option.Option
import io.exko.webawesome.components.select.Select
import io.exko.webawesome.props.SelectPlacement
import io.exko.webawesome.props.SimpleAppearance
import io.exko.webawesome.props.Size
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/select")
class SelectPageController {
    @GetMapping
    fun render() = view(SelectPage(), ::PlaygroundLayout)
}

@UI
fun SelectPage() = fragment {
    h1 { +"Select" }

    Basic()
    br
    Multiple()
    br
    Sizes()
    br
    AppearanceAndPlacement()
    br
    WithSlots()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    Select(label = "Favorite fruit", name = "fruit", withLabel = true, appearance = SimpleAppearance.filled) {
        Option(value = "apple") { +"Apple" }
        Option(value = "banana") { +"Banana" }
        Option(value = "cherry") { +"Cherry" }
    }
}

@UI
fun Component.Multiple() {
    h2 { +"Multiple with tags and clear" }
    Select(label = "Tags", name = "tags", withLabel = true, multiple = true, withClear = true, maxOptionsVisible = 4) {
        Option(value = "alpha") { +"Alpha" }
        Option(value = "bravo") { +"Bravo" }
        Option(value = "charlie") { +"Charlie" }
        Option(value = "delta") { +"Delta" }
        Divider()
        Option(value = "epsilon") { +"Epsilon" }
        Option(value = "fargate") { +"Fargate" }
        Option(value = "gordon") { +"Gordon" }
    }
}

@UI
fun Component.Sizes() {
    h2 { +"Sizes" }
    Size.entries.forEach { sz ->
        Select(size = sz, placeholder = "Choose one") {
            Option(value = "1") { +"One" }
            Option(value = "2") { +"Two" }
            Option(value = "3") { +"Three" }
        }
    }
}

@UI
fun Component.AppearanceAndPlacement() {
    h2 { +"Appearance and Placement" }
    Select(appearance = SimpleAppearance.filled, placement = SelectPlacement.bottom, placeholder = "Filled bottom") {
        Option(value = "1") { +"One" }
        Option(value = "2") { +"Two" }
    }
    Select(appearance = SimpleAppearance.outlined, placement = SelectPlacement.top, placeholder = "Outlined top") {
        Option(value = "1") { +"One" }
        Option(value = "2") { +"Two" }
    }
}

@UI
fun Component.WithSlots() {
    h2 { +"Slots: label and hint" }
    Select(withLabel = true, withHint = true) {
        slotLabel { +"Slotted label" }
        slotHint { +"Choose from the options" }
        Option(value = "a") { +"A" }
        Option(value = "b") { +"B" }
    }
}
