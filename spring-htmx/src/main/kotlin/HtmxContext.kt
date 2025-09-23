package io.exko.htmx.spring

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.WebRequest

data class HTMX(
    val isHxRequest: Boolean,
    var pageTitle: String? = null,
)

fun WebRequest.htmx(block: HTMX.() -> String): String {
    val context = HTMX(isHxRequest)
    return context.block()
}

fun WebRequest.htmxContext(): HTMX {
    return HTMX(isHxRequest)
}

fun HttpServletRequest.htmxContext(): HTMX {
    return HTMX(isHxRequest)
}

val WebRequest.isHxRequest
    get() = !getHeader("HX-Request").isNullOrEmpty()

val HttpServletRequest.isHxRequest
    get() = !getHeader("HX-Request").isNullOrEmpty()
