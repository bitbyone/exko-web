package io.exko.sandbox.uikit.components.tag

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.tag.Tag
import io.exko.webawesome.props.Appearance
import io.exko.webawesome.props.Size
import io.exko.webawesome.props.Variant
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/tag")
class TagPageController {
    @GetMapping
    fun render() = view(TagPage(), ::PlaygroundLayout)
}

@UI
fun TagPage() = fragment {
    h1 { +"Tag" }

    Variants()
    br
    Appearances()
    br
    Sizes()
    br
    PillAndRemove()
}

@UI
fun Component.Variants() {
    h2 { +"Variants" }
    Variant.entries.forEach { v ->
        Tag {
            variant = v
            +v.name
        }
    }
}

@UI
fun Component.Appearances() {
    h2 { +"Appearances" }
    Appearance.entries.forEach { ap ->
        Tag {
            appearance = ap
            +ap.name
        }
    }
}

@UI
fun Component.Sizes() {
    h2 { +"Sizes" }
    Size.entries.forEach { sz ->
        Tag(size = sz) { +"Size ${sz.name}" }
    }
}

@UI
fun Component.PillAndRemove() {
    h2 { +"Pill & Removable" }
    Tag(pill = true) { +"Pill" }
    Tag(withRemove = true) { +"Removable" }
}
