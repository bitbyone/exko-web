package io.exko.sandbox.uikit.components.button

import io.exko.html.Component
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.icon.Icon
import io.exko.webawesome.props.Appearance.*
import io.exko.webawesome.props.Size
import io.exko.webawesome.props.Variant
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/button")
class ButtonPageController {

    @GetMapping
    fun render() = view(ButtonPage(), ::PlaygroundLayout)
}

fun ButtonPage() = fragment {
    h1 { +"Button" }

    Variants()
    br
    Appearances()
    br
    Sizes()
    br
    Slots()
    br
    Disabled()
    br
    Loading()
}

fun Component.Variants() {
    h2 { +"Variant" }
    Variant.entries.forEach {
        Button {
            variant = it
            +it.name
        }
    }
}

fun Component.Appearances() {
    h2 { +"Appearance" }
    entries.forEach {
        Button {
            appearance = +it
            +it.name
        }
    }
    Button(appearance = filled + outlined) { +"filled outlined" }
    Button(appearance = plain + outlined) { +"plain outlined" }
}

fun Component.Sizes() {
    h2 { +"Size" }
    Size.entries.forEach {
        Button(size = it) { +it.name }
    }
}

fun Component.Slots() {
    h2 { +"Slots" }
    Button {
        slotStart {
            Icon(name = "google", family = "brands")
        }
        +"Log In"
        slotEnd {
            Icon(name = "google", family = "brands")
        }
    }
}

fun Component.Disabled() {
    h2 { +"Disabled" }
    Button {
        disabled = true
        +"Disabled"
    }
    Button {
        disabled = false
        +"non Disabled"
    }
}

fun Component.Loading() {
    h2 { +"Loading" }
    Button {
        loading = true
        +"Loading"
    }
    Button {
        loading = false
        +"non Loading"
    }
    Button {
        loading = true
        disabled = true
        +"Loading Disabled"
    }
}

fun Component.Pill() {
    h2 { +"Pill" }
    Button(pill = true) {
        +"Pill"
    }
}
