package io.exko.sandbox.uikit.components.card

import io.exko.html.*
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.Rating
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.card.Card
import io.exko.webawesome.props.Appearance
import io.exko.webawesome.props.Appearance.filled
import io.exko.webawesome.props.Appearance.filledOutlined
import io.exko.webawesome.props.Appearance.outlined
import io.exko.webawesome.props.Variant
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/card")
class CardPageController {

    @GetMapping
    fun render() = view(CardPage(), ::PlaygroundLayout)
}

@UI
fun CardPage() = fragment {
    h1 { +"Card" }

    Basic()
    br
    Overview()
    br
    Appearances()
    br
    SsrFlags()
    br
    Slots()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    Card {
        p { +"This is a basic card with default appearance (outlined)." }
    }
}

@UI
fun Component.Overview() {
    h2 { +"Overview" }
    style {
        raw css """
                .card-overview {
                    max-width: 300px;
                }
        """.trimIndent()
    }
    Card("card-overview") {
        slotMedia {
            img(
                src = "https://images.unsplash.com/photo-1559209172-0ff8f6d49ff7?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=80",
                alt = "A kitten sits patiently between a terracotta pot and decorative grasses.",
            ) {
            }
        }

        strong { +"Mittens" }
        br
        +"This kitten is as cute as he is playful. Bring him home today!"
        br
        small(classes = "wa-caption-m") { +"6 weeks old" }

        slotFooter {
            div("wa-split") {
                Button(variant = Variant.brand, pill = true) { +"More Info" }
                Rating(label = "Rating", max = 10)
            }
        }
    }
}

@UI
fun Component.Appearances() {
    h2 { +"Appearances" }
    Appearance.entries.forEach { appearance ->
        Card(appearance = appearance) {
            p { +"Appearance: ${appearance.name}" }
        }
    }
    // Combination example
    Card(appearance = filledOutlined) {
        p { +"filled outlined" }
    }
}

@UI
fun Component.SsrFlags() {
    h2 { +"SSR Flags (with-*)" }
    Card(withHeader = true) {
        p { +"withHeader set to true (SSR)" }
    }
    Card(withMedia = true) {
        p { +"withMedia set to true (SSR)" }
    }
    Card(withFooter = true) {
        p { +"withFooter set to true (SSR)" }
    }
}

@UI
fun Component.Slots() {
    h2 { +"Slots: header, media, footer" }
    Card {
        slotHeader {
            strong { +"Card Header" }
        }
        slotMedia {
            // Simple placeholder for media
            div {
                style = "background:#eee; padding: 12px; text-align:center;"
                +"MEDIA"
            }
        }
        p { +"This is the card's main content area." }
        slotFooter {
            em { +"Card Footer" }
        }
    }
}
