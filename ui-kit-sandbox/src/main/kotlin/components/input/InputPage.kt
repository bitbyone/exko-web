package io.exko.sandbox.uikit.components.input

import io.exko.html.Component
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.UiKitLayout
import io.exko.webawesome.components.input.Input
import io.exko.webawesome.props.Appearance
import io.exko.webawesome.props.InputType
import io.exko.webawesome.props.Size
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/input")
class InputPageController {

    @GetMapping
    fun render() = view(InputPage(), ::UiKitLayout)
}

fun InputPage() = fragment {
    h1 { +"Input" }

    Basic()
    br
    Sizes()
    br
    Appearances()
    br
    Placeholder()
    br
    ClearButton()
    br
    Pill()
    br
    Readonly()
    br
    Slots()
    br
    Password()
}

fun Component.Basic() {
    h2 { +"Basic" }
    Input(label = "Your name", name = "name")
}

fun Component.Sizes() {
    h2 { +"Sizes" }
    Size.entries.forEach {
        Input(size = it, label = "Size: ${it.name}")
    }
}

fun Component.Appearances() {
    h2 { +"Appearances" }
    Appearance.entries.forEach {
        Input(appearance = +it, label = "Appearance: ${it.name}")
    }
}

fun Component.Placeholder() {
    h2 { +"Placeholder" }
    Input(placeholder = "Type here...", label = "With placeholder")
}

fun Component.ClearButton() {
    h2 { +"With Clear Button" }
    Input(withClear = true, value = "clear me", label = "Clearable")
}

fun Component.Pill() {
    h2 { +"Pill" }
    Input(pill = true, placeholder = "Rounded input", label = "Pill input")
}

fun Component.Readonly() {
    h2 { +"Readonly" }
    Input(readonly = true, value = "Can't edit", label = "Readonly")
}

fun Component.Slots() {
    h2 { +"Slots" }
    Input(label = "With start/end and hint") {
        slotStart { span { +"🔍" } }
        slotEnd { span { +"⏎" } }
        slotHint { span { +"This is a helpful hint (slot)." } }
    }
    Input(type = InputType.text) {
        slotLabel { span { +"Label via slot" } }
    }
}

fun Component.Password() {
    h2 { +"Password with toggle and custom icons" }
    Input(type = InputType.password, passwordToggle = true, label = "Password") {
        slotShowPasswordIcon { span { +"👁️" } }
        slotHidePasswordIcon { span { +"🙈" } }
    }
}
