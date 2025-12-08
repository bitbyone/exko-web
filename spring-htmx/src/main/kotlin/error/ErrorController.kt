package io.exko.htmx.spring.error

import io.exko.html.*
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.ServletException
import kotlinx.html.br
import kotlinx.html.code
import kotlinx.html.div
import org.springframework.boot.web.servlet.error.ErrorController
//import org.springframework.boot.webmvc.error.ErrorController
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.ErrorResponseException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

private val log = KotlinLogging.logger { }

@ControllerAdvice
class ErrorController(val layoutProvider: ErrorLayoutProvider) : ErrorController {

    @ExceptionHandler(Exception::class)
    fun handleError(e: Exception): Render {
        log.error(e) { "Unexpected Server Error occurred" }
        val stacktrace = e.stackTraceToString()
        return view(
            page = fragment {
                div {
                    +"${e.message}"
                }
                br
                code {
                    +stacktrace.lineSequence().take(8).joinToString(
                        separator = "\n"
                    )
                }
            },
            layout = layoutProvider::errorLayout,
        )
    }

    @ExceptionHandler(ErrorResponseException::class, ServletException::class)
    fun handlerMissingResource(e: Exception, request: WebRequest): ResponseEntity<*> {
        if (e is ErrorResponse) {
            // TODO we need to somehow render proper 404 page for htmx and full refresh
//            if (request.isHxRequest) {
//                return ResponseEntity.ok("Not Found")
//            }
            log.debug { "Error response [${e.statusCode.value()}]: ${e.message} (${e::class.simpleName})" }
            return ResponseEntity.status(e.statusCode).build<Unit>()
        }
        return ResponseEntity.internalServerError().build<Unit>()
    }
}

fun interface ErrorLayoutProvider {
    fun errorLayout(children: Children): Renderable
}
