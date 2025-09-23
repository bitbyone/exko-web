package io.exko.sandbox.uikit.components.tabs

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.tabs.Tab
import io.exko.webawesome.components.tabs.TabGroup
import io.exko.webawesome.components.tabs.TabPanel
import io.exko.webawesome.props.Activation
import io.exko.webawesome.props.TabsPlacement
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/tabs")
class TabsPageController {

    @GetMapping
    fun render() = view(TabsPage(), ::PlaygroundLayout)
}

@UI
fun TabsPage() = fragment {
    h1 { +"Tab Group" }

    Basic()
    br
    Placements()
    br
    ManualActivation()
    br
    DisabledTab()
    br
    WithoutScrollControls()
}

fun Component.Basic() {
    h2 { +"Basic" }
    TabGroup(active = "custom") {
        Tab(panel = "general") { +"General" }
        Tab(panel = "custom") { +"Custom" }
        Tab(panel = "advanced") { +"Advanced" }

        TabPanel(name = "general") {
            +"This is the general panel content."
        }
        TabPanel(name = "custom") {
            +"This is the custom panel content."
        }
        TabPanel(name = "advanced") {
            +"This is the advanced panel content."
        }
    }
}

@UI
fun Component.Placements() {
    h2 { +"Placements" }
    // bottom
    TabGroup(active = "one", placement = TabsPlacement.bottom) {
        Tab(panel = "one") { +"One" }
        Tab(panel = "two") { +"Two" }
        Tab(panel = "three") { +"Three" }
        TabPanel(name = "one", active = true) { +"Bottom placement panel One" }
        TabPanel(name = "two") { +"Bottom placement panel Two" }
        TabPanel(name = "three") { +"Bottom placement panel Three" }
    }
    br
    // start (vertical)
    TabGroup(active = "alpha", placement = TabsPlacement.start) {
        Tab(panel = "alpha") { +"Alpha" }
        Tab(panel = "beta") { +"Beta" }
        Tab(panel = "gamma") { +"Gamma" }
        TabPanel(name = "alpha", active = true) { +"Start placement Alpha" }
        TabPanel(name = "beta") { +"Start placement Beta" }
        TabPanel(name = "gamma") { +"Start placement Gamma" }
    }
    br
    // end (vertical)
    TabGroup(active = "i", placement = TabsPlacement.end) {
        Tab(panel = "i") { +"I" }
        Tab(panel = "ii") { +"II" }
        Tab(panel = "iii") { +"III" }
        TabPanel(name = "i", active = true) { +"End placement I" }
        TabPanel(name = "ii") { +"End placement II" }
        TabPanel(name = "iii") { +"End placement III" }
    }
}

@UI
fun Component.ManualActivation() {
    h2 { +"Manual activation" }
    TabGroup(active = "first", activation = Activation.manual) {
        Tab(panel = "first") { +"First" }
        Tab(panel = "second") { +"Second" }
        Tab(panel = "third") { +"Third" }
        TabPanel(name = "first", active = true) { +"Manual: activate with Enter/Space" }
        TabPanel(name = "second") { +"Second manual panel" }
        TabPanel(name = "third") { +"Third manual panel" }
    }
}

@UI
fun Component.DisabledTab() {
    h2 { +"Disabled tab" }
    TabGroup(active = "a") {
        Tab(panel = "a") { +"A" }
        Tab(panel = "b", disabled = true) { +"B (disabled)" }
        Tab(panel = "c") { +"C" }
        TabPanel(name = "a", active = true) { +"Panel A" }
        TabPanel(name = "b") { +"Panel B" }
        TabPanel(name = "c") { +"Panel C" }
    }
}

@UI
fun Component.WithoutScrollControls() {
    h2 { +"Without scroll controls" }
    TabGroup(active = "t1", withoutScrollControls = true) {
        (1..8).forEach { i ->
            Tab(panel = "t$i") { +"Tab $i" }
        }
        (1..8).forEach { i ->
            TabPanel(name = "t$i", active = i == 1) { +"Panel $i" }
        }
    }

    h2 { +"With scroll controls" }
    TabGroup(active = "t1", withoutScrollControls = false) {
        (1..8).forEach { i ->
            Tab(panel = "t$i") { +"Tab $i" }
        }
        (1..8).forEach { i ->
            TabPanel(name = "t$i", active = i == 1) { +"Panel $i" }
        }
    }
}
