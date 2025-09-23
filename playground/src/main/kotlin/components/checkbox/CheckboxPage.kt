package io.exko.sandbox.uikit.components.checkbox

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.checkbox.Checkbox
import io.exko.webawesome.props.Size
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/checkbox")
class CheckboxPageController {
    @GetMapping
    fun render() = view(CheckboxPage(), ::PlaygroundLayout)
}

@UI
fun CheckboxPage() = fragment {
    h1 { +"Checkbox" }

    Basic()
    br
    Sizes()
    br
    Disabled()
    br
    Indeterminate()
    br
    WithHint()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    Checkbox(name = "subscribe", value = "yes") { +"Subscribe to newsletter" }
}

@UI
fun Component.Sizes() {
    h2 { +"Sizes" }
    Size.entries.forEach { sz ->
        Checkbox(size = sz) { +"Size ${sz.name}" }
    }
}

@UI
fun Component.Disabled() {
    h2 { +"Disabled" }
    Checkbox(disabled = true, checked = true) { +"Disabled checked" }
    Checkbox(disabled = true) { +"Disabled" }
}

@UI
fun Component.Indeterminate() {
    h2 { +"Indeterminate" }
    Checkbox(indeterminate = true) { +"Parent selection" }
}

@UI
fun Component.WithHint() {
    h2 { +"With hint" }
    Checkbox(hint = "This action can be changed later") { +"Enable feature" }
}
