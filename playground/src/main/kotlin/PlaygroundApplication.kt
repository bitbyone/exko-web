package io.exko.sandbox.uikit

import io.exko.html.Children
import io.exko.html.Component
import io.exko.html.UI
import io.exko.htmx.use
import io.exko.sandbox.uikit.layout.PageContentSpecs
import io.exko.sandbox.uikit.layout.htmlTemplate
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.props.Appearance
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.id
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class PlaygroundApplication

fun main(args: Array<String>) {
    runApplication<PlaygroundApplication>(*args)
}

@Controller
class IndexController {
    @GetMapping("/")
    fun index() = "redirect:/ui-kit/button"
}

@RestController
class StimulusController {
    @GetMapping("/__stimulus/controllers")
    fun findControllers(): List<String> {
        val resolver = PathMatchingResourcePatternResolver()
        val root = "/public/controllers"
        val resources = resolver.getResources("classpath*:public/controllers/**/*.js")
        return resources.mapNotNull { it.url.path.substringAfter(root) }
    }
}

@UI
fun PlaygroundLayout(content: Children) = htmlTemplate {
    h1 { +"Exko Playground" }
    div("wa-flank wa-align-items-start") {
        attributes["style"] = "margin: var(--wa-space-xl);"
        div("wa-split:column") {
            div("wa-stack wa-gap-0") {
                MenuItem("/ui-kit/avatar", "Avatar")
                MenuItem("/ui-kit/icon", "Icon")
                MenuItem("/ui-kit/button", "Button")
                MenuItem("/ui-kit/button-group", "Button Group")
                MenuItem("/ui-kit/badge", "Badge")
                MenuItem("/ui-kit/callout", "Callout")
                MenuItem("/ui-kit/card", "Card")
                MenuItem("/ui-kit/divider", "Divider")
                MenuItem("/ui-kit/popover", "Popover")
                MenuItem("/ui-kit/tooltip", "Tooltip")
                MenuItem("/ui-kit/details", "Details")
                MenuItem("/ui-kit/tabs", "Tabs")
                MenuItem("/ui-kit/dropdown", "Dropdown")
                MenuItem("/ui-kit/input", "Input")
                MenuItem("/ui-kit/radio", "Radio")
                MenuItem("/ui-kit/select", "Select")
                MenuItem("/ui-kit/textarea", "Textarea")
                MenuItem("/ui-kit/switch", "Switch")
                MenuItem("/ui-kit/tag", "Tag")
                MenuItem("/ui-kit/spinner", "Spinner")
                MenuItem("/ui-kit/slider", "Slider")
                MenuItem("/ui-kit/skeleton", "Skeleton")
                MenuItem("/ui-kit/tree", "Tree")
                MenuItem("/ui-kit/checkbox", "Checkbox")
                MenuItem("/ui-kit/dialog", "Dialog")
                MenuItem("/ui-kit/drawer", "Drawer")
                MenuItem("/ui-kit/rating", "Rating")
                MenuItem("/ui-kit/copy-button", "Copy Button")
                MenuItem("/ui-kit/breadcrumb", "Breadcrumb")
            }
        }
        div {
            id = PageContentSpecs.id
            content()
        }
    }
}

@UI
private fun Component.MenuItem(path: String, label: String) {
    Button(appearance = Appearance.plain) {
        attributes["hx-get"] = path
        attributes["hx-push-url"] = "true"
        use(PageContentSpecs)
        +label
    }
}
