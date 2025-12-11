package io.exko.htmx.spring

import io.exko.html.*
import jakarta.servlet.http.HttpServletResponse
import kotlinx.html.consumers.delayed
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.stream.HTMLStreamBuilder
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer

class HtmxResponseHandler : HandlerMethodReturnValueHandler {

    override fun supportsReturnType(returnType: MethodParameter): Boolean {
        return returnType.parameterType == Render::class.java
    }

    override fun handleReturnValue(
        returnValue: Any?,
        returnType: MethodParameter,
        mavContainer: ModelAndViewContainer,
        webRequest: NativeWebRequest
    ) {
        val response = webRequest.getNativeResponse(HttpServletResponse::class.java)
        requireNotNull(response)

        response.contentType = "text/html;charset=UTF-8"
        val streamRenderer = HTMLStreamBuilder(response.writer, true, false).delayed()
        val render = returnValue as Render
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
        response.setHeader("Pragma", "no-cache")
        response.setHeader("Expires", "0")
        if (render.headers.isNotEmpty()) {
            render.headers.forEach { (name, value) ->
                response.setHeader(name, value)
            }
        }
        val htmxContext = webRequest.getAttribute(HTMX_CONTEXT_ATTR, WebRequest.SCOPE_REQUEST) as HTMX

        if (htmxContext.isHxRequest) {
            render {
                // TODO this should be inside htmx rendering module?
                div("hx-fragment-target" + if (render.fragmentClasses != null) " ${render.fragmentClasses}" else "") {
                    val fragmentId = getRandomString()
                    val _id = "hx-fragment-$fragmentId"
                    id = _id
                    render.render(this@div)
                }
            }(streamRenderer)
        } else {
            response.writer.appendLine("<!DOCTYPE html>")
            render.layout {
                div("hx-fragment-target") {
                    render.render(this@layout)
                }
            }(streamRenderer)
        }

        mavContainer.isRequestHandled = true
    }
}
