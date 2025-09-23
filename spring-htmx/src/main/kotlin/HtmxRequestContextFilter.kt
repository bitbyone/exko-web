package io.exko.htmx.spring

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

const val HTMX_CONTEXT_ATTR = "HTMX_CONTEXT"

class HtmxRequestContextFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val htmx = request.htmxContext()
        request.setAttribute(HTMX_CONTEXT_ATTR, htmx)
        filterChain.doFilter(request, response)
    }
}
