package io.exko.sandbox.uikit.components.slider

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.slider.Slider
import io.exko.webawesome.props.Orientation
import io.exko.webawesome.props.SimplePlacement
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.span
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/slider")
class SliderPageController {
    @GetMapping
    fun render() = view(SliderPage(), ::PlaygroundLayout)
}

@UI
fun SliderPage() = fragment {
    h1 { +"Slider" }

    Basic()
    br
    Range()
    br
    WithMarkersAndTooltip()
    br
    Vertical()
    br
    UberSlider()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    Slider(label = "Volume", withLabel = true, min = 0, max = 100, value = 25)
}

@UI
fun Component.Range() {
    h2 { +"Range" }
    Slider(label = "Price", withLabel = true, range = true, min = 0, max = 100, minValue = 20, maxValue = 80)
}

@UI
fun Component.WithMarkersAndTooltip() {
    h2 { +"With markers and tooltip" }
    Slider(min = 0, max = 10, step = 1, withMarkers = true, withTooltip = true)
}

@UI
fun Component.Vertical() {
    h2 { +"Vertical" }
    Slider(orientation = Orientation.vertical, min = 0, max = 10, value = 4)
}

@UI
fun Component.UberSlider() {
    h2 { +"Uber Slider" }
    Slider(
        label = "Scale",
        range = true,
        step = 1,
        min = 1,
        max = 7,
        minValue = 3,
        maxValue = 6,
        withMarkers = true,
        withTooltip = true,
        tooltipPlacement = SimplePlacement.bottom,
    ) {
        slotReference { span { +"Low" } }
        slotReference { span { +"Medium" } }
        slotReference { span { +"High" } }
    }
}
