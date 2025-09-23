package io.exko.htmx.spring

import io.exko.html.*
import io.exko.htmx.spring.error.ErrorController
import io.exko.htmx.spring.error.ErrorLayoutProvider
import kotlinx.html.div
import kotlinx.html.h1
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfiguration
class HtmxMvcAutoConfiguration : WebMvcConfigurer {

    @Bean
    fun htmxContextFilter() = HtmxRequestContextFilter()

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(HtmxArgResolver())
    }

    override fun addReturnValueHandlers(handlers: MutableList<HandlerMethodReturnValueHandler>) {
        handlers.add(0, HtmxResponseHandler())
    }

    @ConditionalOnMissingBean
    @Bean
    fun errorController(errorLayoutProvider: ErrorLayoutProvider) = ErrorController(errorLayoutProvider)

    // TODO this default should provide visually nice and functional design
    // User should not have need to override default layout, it should just work
    @ConditionalOnMissingBean
    @Bean
    fun errorLayoutProvider(): ErrorLayoutProvider {
        return ErrorLayoutProvider { children ->
            render {
                div("error-view") {
                    h1 { +"Error" }
                    children()
                }
            }
        }
    }

}
