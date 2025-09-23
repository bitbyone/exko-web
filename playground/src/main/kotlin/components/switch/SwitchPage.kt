package io.exko.sandbox.uikit.components.switch

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.switch.Switch
import io.exko.webawesome.props.Size
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/switch")
class SwitchPageController {
    @GetMapping
    fun render() = view(SwitchPage(), ::PlaygroundLayout)
}

@UI
fun SwitchPage() = fragment {
    h1 { +"Switch" }

    Basics()
    br
    Sizes()
    br
    WithHint()
}

@UI
fun Component.Basics() {
    h2 { +"Basics" }
    Switch(name = "newsletter", value = "yes") { +"Subscribe" }
    Switch(name = "disabled", disabled = true, checked = true) { +"Disabled" }
}

@UI
fun Component.Sizes() {
    h2 { +"Sizes" }
    Size.entries.forEach { sz ->
        Switch(size = sz) { +"Size ${sz.name}" }
    }
}

@UI
fun Component.WithHint() {
    h2 { +"With hint (slot)" }
    Switch(withHint = true, hint = "Enables extra notifications") { +"Notifications" }
}
