package io.exko.sandbox.uikit.components.skeleton

import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.skeleton.Skeleton
import io.exko.webawesome.props.SkeletonEffect
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.h2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/skeleton")
class SkeletonPageController {
    @GetMapping
    fun render() = view(SkeletonPage(), ::PlaygroundLayout)
}

@UI
fun SkeletonPage() = fragment {
    h1 { +"Skeleton" }

    h2 { +"Effects" }
    Skeleton(effect = SkeletonEffect.pulse)
    br
    Skeleton(effect = SkeletonEffect.sheen)
    br
    Skeleton(effect = SkeletonEffect.none)
}
