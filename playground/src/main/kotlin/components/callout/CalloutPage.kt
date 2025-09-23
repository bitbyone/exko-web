package io.exko.sandbox.uikit.components.callout

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.callout.Callout
import io.exko.webawesome.props.Appearance
import io.exko.webawesome.props.Appearance.filled
import io.exko.webawesome.props.Appearance.filledOutlined
import io.exko.webawesome.props.Appearance.outlined
import io.exko.webawesome.props.Size
import io.exko.webawesome.props.Variant
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/callout")
class CalloutPageController {

    @GetMapping
    fun render() = view(CalloutPage(), ::PlaygroundLayout)
}

@UI
fun CalloutPage() = fragment {
    h1 { +"Callout" }

    Basic()
    br
    Variants()
    br
    Appearances()
    br
    Sizes()
    br
    IconSlot()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    Callout {
        p { +"This is a simple callout with default settings." }
    }
}

@UI
fun Component.Variants() {
    h2 { +"Variants" }
    Variant.entries.forEach { variant ->
        Callout(variant = variant) {
            p {
                strong { +"Header here!" }
                br
                +"Variant: ${variant.name}"
            }
        }
    }
}

@UI
fun Component.Appearances() {
    h2 { +"Appearances" }
    Variant.entries.forEach { variant ->
        Appearance.entries.forEach { appearance ->
            Callout(variant = variant, appearance = appearance) {
                p {
                    strong { +"Header here!" }
                    br
                    +"${variant.name} / ${appearance.name}"
                }
            }
        }
        // Combination example
        Callout(variant = variant, appearance = filledOutlined) {
            p { +"${variant.name} / filled outlined" }
        }
        br
    }
}

@UI
fun Component.Sizes() {
    h2 { +"Sizes" }
    Size.entries.forEach { size ->
        Callout(size = size) {
            p { +"Size: ${size.name}" }
        }
    }
}

@UI
fun Component.IconSlot() {
    h2 { +"Icon Slot" }
    Callout {
        slotIcon {
            // Using a simple text/emoji as an icon placeholder; a real project can use <wa-icon>
            span { +"ℹ️" }
        }
        p { +"This callout shows an icon provided via the icon slot." }
    }
}
