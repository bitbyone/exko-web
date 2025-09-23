package io.exko.sandbox.uikit.components.textarea

import io.exko.html.Component
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.UiKitLayout
import io.exko.webawesome.components.textarea.Textarea
import io.exko.webawesome.props.InputMode
import io.exko.webawesome.props.SimpleAppearance
import io.exko.webawesome.props.Size
import io.exko.webawesome.props.Resize
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/textarea")
class TextareaPageController {
    @GetMapping
    fun render() = view(TextareaPage(), ::UiKitLayout)
}

fun TextareaPage() = fragment {
    h1 { +"Textarea" }

    Basic()
    br
    Sizes()
    br
    Appearance()
    br
    ResizeOptions()
    br
    WithSlots()
}

fun Component.Basic() {
    h2 { +"Basic" }
    Textarea(label = "Description", withLabel = true, placeholder = "Type here...")
}

fun Component.Sizes() {
    h2 { +"Sizes" }
    Size.entries.forEach { sz ->
        Textarea(size = sz, placeholder = "Size ${sz.name}")
    }
}

fun Component.Appearance() {
    h2 { +"Appearance" }
    Textarea(appearance = SimpleAppearance.filled, placeholder = "Filled")
    Textarea(appearance = SimpleAppearance.outlined, placeholder = "Outlined")
}

fun Component.ResizeOptions() {
    h2 { +"Resize" }
    val variants = listOf(
        Resize.none,
        Resize.vertical,
        Resize.horizontal,
        Resize.both,
        Resize.auto
    )
    variants.forEach { r ->
        Textarea(resize = r, rows = 3, placeholder = r.realValue)
    }
}

fun Component.WithSlots() {
    h2 { +"Slots: label & hint" }
    Textarea(withLabel = true, withHint = true, hint = "xxxx", label = "xxxxxxxx", inputmode = InputMode.text) {
        slotLabel { +"Slotted label" }
        slotHint { +"Helpful hint for textarea" }
    }
}
