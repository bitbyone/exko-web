package io.exko.sandbox.uikit.components

import io.exko.html.Component
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.breadcrumb.Breadcrumb
import io.exko.webawesome.components.breadcrumb.BreadcrumbItem
import io.exko.webawesome.components.icon.Icon
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.span
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/breadcrumb")
class BreadcrumbPageController {

    @GetMapping
    fun render() = view(BreadcrumbPage(), ::PlaygroundLayout)
}

fun BreadcrumbPage() = fragment {
    h1 { +"Breadcrumb" }

    Basic()
    br
    CustomSeparator()
    br
    ItemSpecificSeparator()
}

fun Component.Basic() {
    h2 { +"Basic" }
    Breadcrumb(label = "Breadcrumb Navigation") {
        BreadcrumbItem(href = "#") {
            slotStart { Icon(name = "house") }
            +"Home" }
        BreadcrumbItem(href = "#") { +"Library" }
        BreadcrumbItem { +"Data" }
    }
}

fun Component.CustomSeparator() {
    h2 { +"Custom separator for all items" }
    Breadcrumb(label = "Breadcrumb with custom separator") {
        slotSeparator {
            span { +"/" }
        }
        BreadcrumbItem(href = "#") { +"Home" }
        BreadcrumbItem(href = "#") { +"Category" }
        BreadcrumbItem { +"Current" }
    }
}

fun Component.ItemSpecificSeparator() {
    h2 { +"Item-specific separator override" }
    Breadcrumb(label = "Breadcrumb with item-specific separator") {
        BreadcrumbItem(href = "#") { +"Level 1" }
        BreadcrumbItem(href = "#") {
            slotSeparator { span { +"→" } }
            +"Level 2"
        }
        BreadcrumbItem { +"Level 3" }
    }
}
