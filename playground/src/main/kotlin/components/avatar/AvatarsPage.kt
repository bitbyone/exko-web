package io.exko.sandbox.uikit.components.avatar

import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.avatar.Avatar
import io.exko.webawesome.props.ImageLoading
import io.exko.webawesome.props.Shape
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/avatar")
class AvatarPageController {

    @GetMapping
    fun render() = view(AvatarPage(), ::PlaygroundLayout)
}

@UI
fun AvatarPage() = fragment {
    h1 { +"Avatar" }

    h2 { +"Default" }
    Avatar(label = "Avatar")

    h2 { +"Initials" }
    Avatar(initials = "JD", label = "John Doe")

    h2 { +"Shapes" }
    Shape.entries.forEach {
        Avatar(initials = "JD", label = "John Doe", shape = it)
    }

    h2 { +"Images" }
    Avatar(
        image = "https://images.unsplash.com/photo-1529778873920-4da4926a72c2?ixlib=rb-1.2.1&auto=format&fit=crop&w=300&q=80",
        label = "Avatar of gray tabby kitten looking down",
    )
    Avatar(
        image = "https://images.unsplash.com/photo-1591871937573-74dbba515c4c?ixlib=rb-1.2.1&auto=format&fit=crop&w=300&q=80",
        label = "Avatar of white tabby kitten looking down",
        loading = ImageLoading.lazy,
    )
}
