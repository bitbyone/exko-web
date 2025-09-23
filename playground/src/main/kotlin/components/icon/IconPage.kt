package io.exko.sandbox.uikit.components.icon

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.icon.Icon
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.p
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/icon")
class IconPageController {

    @GetMapping
    fun render() = view(IconPage(), ::PlaygroundLayout)
}

@UI
fun IconPage() = fragment {
    h1 { +"Icon" }

    Basic()
    VariantsAndFamilies()
    FromSrc()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    div("wa-cluster wa-gap-l wa-align-items-center") {
        // default classic/regular if available in library
        Icon(name = "user", label = "User icon")
        Icon(name = "gear", label = "Gear icon")
        Icon(name = "heart", label = "Heart icon")
    }
}

@UI
fun Component.VariantsAndFamilies() {
    h2 { +"Family, Variant, Auto Width" }
    div("wa-cluster wa-gap-l wa-align-items-center") {
        attributes["style"] = "font-size: 1.9rem;"
        Icon(name = "user", family = "classic", variant = "regular", label = "Classic Regular")
        Icon(name = "user", family = "classic", variant = "solid", autoWidth = true, label = "Classic Solid Auto width")
        Icon(name = "github", family = "brands", label = "Github")
        Icon(name = "reddit", family = "brands", label = "Github")
        Icon(name = "google", family = "brands", label = "Github")
        Icon(name = "stack-overflow", family = "brands", label = "Github")
        Icon(name = "fedora", family = "brands", label = "Github")
    }
}

@UI
fun Component.FromSrc() {
    h2 { +"External SVG (src)" }
    p { +"Rendering an external SVG via src attribute (example uses a simple SVG data URI)." }
    // Use a tiny data URI SVG to avoid external network
    val dataSvg = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3E%3Ccircle cx='8' cy='8' r='7' fill='tomato'/%3E%3C/svg%3E"
    div("wa-cluster wa-gap-l wa-align-items-center") {
        Icon(src = dataSvg, label = "External SVG circle")
    }
}
