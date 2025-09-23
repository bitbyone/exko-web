package io.exko.sandbox.uikit.components.badge

import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.badge.Badge
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.props.Appearance
import io.exko.webawesome.props.Appearance.filled
import io.exko.webawesome.props.Appearance.filledOutlined
import io.exko.webawesome.props.Appearance.outlined
import io.exko.webawesome.props.Attention.bounce
import io.exko.webawesome.props.Attention.pulse
import io.exko.webawesome.props.Variant
import io.exko.webawesome.props.Variant.warning
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/badge")
class BadgePageController {

    @GetMapping
    fun render() = view(BadgePage(), ::PlaygroundLayout)
}

@UI
fun BadgePage() = fragment {
    h1 { +"Badge" }

    h2 { +"Variants" }
    Variant.entries.forEach {
        Badge(variant = it) { +"Badge" }
    }

    h2 { +"Appearances" }
    Variant.entries.forEach { variant ->
        Appearance.entries.forEach { appearance ->
            Badge(variant = variant, appearance = appearance) { +"Badge" }
        }
        Badge(variant = variant, appearance = filledOutlined) { +"Badge" }
        br
    }

    h2 { +"Attention" }
    Badge(attention = bounce) { +"1" }
    Badge(attention = pulse) { +"1" }

    h2 { +"With Button" }
    Button {
        +"Notifications"
        Badge(variant = warning, attention = pulse) { +"21" }
    }
}
