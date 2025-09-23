package io.exko.sandbox.uikit.components.spinner

import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.spinner.Spinner
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/spinner")
class SpinnerPageController {
    @GetMapping
    fun render() = view(SpinnerPage(), ::PlaygroundLayout)
}

@UI
fun SpinnerPage() = fragment {
    h1 { +"Spinner" }

    h2 { +"Default" }
    Spinner()

    h2 { +"Styled via classes" }
    div(classes = "wa-stack") {
        Spinner(classes = "wa-color-primary")
        Spinner(classes = "wa-color-success")
        Spinner(classes = "wa-color-warning")
        Spinner(classes = "wa-color-danger")
    }
}
