package io.exko.sandbox.uikit.components.button

import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.button.ButtonGroup
import io.exko.webawesome.props.Orientation
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/button-group")
class ButtonGroupPageController {

    @GetMapping
    fun render() = view(ButtonGroupPage(), ::PlaygroundLayout)
}

@UI
fun ButtonGroupPage() = fragment {
    h1 { +"Button Group" }

    ButtonGroup {
        Button { +"Button 1" }
        Button { +"Button 2" }
        Button { +"Button 3" }
    }

    h2 { +"Horizontal Orientation" }
    ButtonGroup(orientation = Orientation.vertical) {
        Button { +"Button 1" }
        Button { +"Button 2" }
        Button { +"Button 3" }
    }
}
