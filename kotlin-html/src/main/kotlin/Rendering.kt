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
    headers: MutableMap<String, String> = mutableMapOf(),
    fragmentClasses: String? = null
): Render {
    return Render(page, layout, headers, fragmentClasses)
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

data class Render(
    val render: Children,
    val layout: (Children) -> Renderable,
    val headers: MutableMap<String, String> = mutableMapOf(),
    val fragmentClasses: String? = null,
)

typealias Renderable = TagConsumer<*>.() -> Unit

fun getRandomString(length: Int = 8): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
