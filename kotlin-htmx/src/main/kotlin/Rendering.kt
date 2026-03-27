package io.exko.htmx

import io.exko.html.Children
import io.exko.html.Render
import io.exko.html.Renderable
import io.exko.htmx.tag.HX_PARTIAL
import io.exko.htmx.tag.hxPartial
import kotlinx.html.FlowContent

class SwapViewScope {
    internal var triggerEvent: String? = null
    private val blocks = mutableListOf<FlowContent.() -> Unit>()

    fun trigger(event: String) {
        triggerEvent = event
    }

    fun partial(
        swap: String? = null,
        target: String? = null,
        block: HX_PARTIAL.() -> Unit = {},
    ) {
        blocks += { hxPartial(swap, target, block) }
    }

    internal fun toRenderBlock(): FlowContent.() -> Unit = {
        blocks.forEach { it() }
    }
}

fun pageView(
    page: Children,
    layout: (Children) -> Renderable,
    headers: MutableMap<String, String> = mutableMapOf(),
    fragmentClasses: String? = null,
): Render = Render(
    render = page,
    layout = layout,
    headers = headers,
    fragmentClasses = fragmentClasses,
    fragment = true,
)

fun swapView(block: SwapViewScope.() -> Unit): Render {
    val scope = SwapViewScope().apply(block)
    return Render(
        render = scope.toRenderBlock(),
        layout = null,
        headers = if (scope.triggerEvent != null) mutableMapOf("HX-Trigger" to scope.triggerEvent!!) else mutableMapOf(),
        fragment = false,
    )
}
