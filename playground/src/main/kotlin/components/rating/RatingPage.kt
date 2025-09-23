package io.exko.sandbox.uikit.components.rating

import io.exko.html.Component
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.Rating
import io.exko.webawesome.props.Size
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/rating")
class RatingPageController {
    @GetMapping
    fun render() = view(RatingPage(), ::PlaygroundLayout)
}

fun RatingPage() = fragment {
    h1 { +"Rating" }

    Basic()
    br
    Sizes()
    br
    States()
}

fun Component.Basic() {
    h2 { +"Basic" }
    Rating(label = "Rate this product", value = 3, max = 5)
}

fun Component.Sizes() {
    h2 { +"Sizes" }
    listOf(Size.small, Size.medium, Size.large).forEach { s ->
        Rating(label = "Size ${s.name}", size = s, value = 2, max = 5)
        br
    }
}

fun Component.States() {
    h2 { +"States" }
    Rating(label = "Readonly", value = 4, max = 5, readonly = true)
    br
    Rating(label = "Disabled", value = 2, max = 5, disabled = true)
    br
    Rating(label = "Half steps", value = 3, max = 5, precision = 0.5)
}
