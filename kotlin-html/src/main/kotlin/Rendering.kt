package io.exko.html

import kotlinx.html.TagConsumer

inline fun render(crossinline wrap: Renderable): Renderable {
    return {
        wrap()
    }
}

inline fun fragment(crossinline wrap: Children): Children {
    return {
        wrap()
    }
}

inline fun renderable(builder: RenderBuilder.() -> Unit): Render {
    val builder = RenderBuilder()
    builder.builder()
    return builder.build()
}

fun view(
    page: Children,
    layout: (Children) -> Renderable,
    headers: Map<String, String> = emptyMap()
): Render {
    return Render(page, layout, headers)
}

class RenderBuilder {

    var page: Children? = null
    var layout: ((Children) -> Renderable)? = null

    fun build(): Render {
        val p = requireNotNull(page)
        val l = requireNotNull(layout)
        return Render(p, l)
    }
}

data class Render(val render: Children, val layout: (Children) -> Renderable, val headers: Map<String, String> = emptyMap())

typealias Renderable = TagConsumer<*>.() -> Unit

fun getRandomString(length: Int = 8): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
