package io.exko.sandbox.uikit.components.radio

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.radio.Radio
import io.exko.webawesome.components.radio.RadioGroup
import io.exko.webawesome.props.Orientation
import io.exko.webawesome.props.RadioAppearance
import io.exko.webawesome.props.Size
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/radio")
class RadioPageController {
    @GetMapping
    fun render() = view(RadioPage(), ::PlaygroundLayout)
}

@UI
fun RadioPage() = fragment {
    h1 { +"Radio" }

    BasicRadios()
    br
    HorizontalGroup()
    br
    ButtonAppearance()
    br
    Sizes()
    br
    Disabled()
    br
    RequiredWithLabelHint()
    br
    SlotsLabelHint()
}

@UI
fun Component.BasicRadios() {
    h2 { +"Basic (vertical)" }
    RadioGroup(label = "Choose a fruit", name = "fruit", value = "apple") {
        Radio(value = "apple") { +"Apple" }
        Radio(value = "banana") { +"Banana" }
        Radio(value = "cherry") { +"Cherry" }
    }
}

@UI
fun Component.HorizontalGroup() {
    h2 { +"Horizontal group" }
    RadioGroup(label = "Choose a pet", name = "pet", orientation = Orientation.horizontal, value = "cat") {
        Radio(value = "cat") { +"Cat" }
        Radio(value = "dog") { +"Dog" }
        Radio(value = "bird") { +"Bird" }
    }
}

@UI
fun Component.ButtonAppearance() {
    h2 { +"Button appearance & Orientation" }
    Orientation.entries.forEach { orientation ->
        RadioGroup(label = "Size", name = "btn-size", orientation = orientation, value = "m") {
            Radio(value = "s", appearance = RadioAppearance.button) { +"Small" }
            Radio(value = "m", appearance = RadioAppearance.button) { +"Medium" }
            Radio(value = "l", appearance = RadioAppearance.button) { +"Large" }
        }
    }
}

@UI
fun Component.Sizes() {
    h2 { +"Sizes" }
    Size.entries.forEach { sz ->
        RadioGroup(label = "${sz.name} group", name = "sizes-${sz.name}", size = sz, value = "2") {
            Radio(value = "1") { +"One" }
            Radio(value = "2") { +"Two" }
            Radio(value = "3") { +"Three" }
        }
    }
}

@UI
fun Component.Disabled() {
    h2 { +"Disabled" }
    RadioGroup(label = "Disabled group", name = "disabled-group", disabled = true, value = "b") {
        Radio(value = "a") { +"A" }
        Radio(value = "b") { +"B" }
        Radio(value = "c") { +"C" }
    }
}

@UI
fun Component.RequiredWithLabelHint() {
    h2 { +"Required with label and hint" }
    RadioGroup(
        label = "Select one option",
        hint = "You must choose exactly one",
        name = "required-example",
        required = true,
        withLabel = true,
        withHint = true,
        value = "2",
    ) {
        Radio(value = "1") { +"Option 1" }
        Radio(value = "2") { +"Option 2" }
        Radio(value = "3") { +"Option 3" }
    }
}

@UI
fun Component.SlotsLabelHint() {
    h2 { +"Slots: label & hint" }
    RadioGroup(name = "slotted", withLabel = true, withHint = true, value = "b") {
        slotLabel { +"Slotted Group Label" }
        slotHint { +"This hint is provided via the hint slot." }
        Radio(value = "a") { +"Alpha" }
        Radio(value = "b") { +"Bravo" }
        Radio(value = "c") { +"Charlie" }
    }
}
