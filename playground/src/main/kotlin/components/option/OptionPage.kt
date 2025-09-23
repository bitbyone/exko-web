package io.exko.sandbox.uikit.components.option

import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.option.Option
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/option")
class OptionPageController {
    @GetMapping
    fun render() = view(OptionPage(), ::PlaygroundLayout)
}

@UI
fun OptionPage() = fragment {
    h1 { +"Option" }

    h2 { +"Basic" }
    Option(value = "a") { +"Alpha" }
    Option(value = "b", disabled = true) { +"Disabled" }
}
