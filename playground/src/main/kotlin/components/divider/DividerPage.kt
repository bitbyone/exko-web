package io.exko.sandbox.uikit.separator

import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.divider.Divider
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/divider")
class SeparatorPageController {

    @GetMapping
    fun render() = view(DividerPage(), ::PlaygroundLayout)
}

@UI
fun DividerPage() = fragment {
    h1 { +"Divider" }

    h2 { +"Basic" }
    p { +"Above" }
    Divider()
    p { +"Below" }

    br

    h2 { +"In a stack" }
    div("wa-stack") {
        p { +"Item 1" }
        Divider()
        p { +"Item 2" }
        Divider()
        p { +"Item 3" }
    }
}
