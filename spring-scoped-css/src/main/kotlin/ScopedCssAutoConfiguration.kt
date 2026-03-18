package io.exko.scopedcss.spring

import io.exko.htmx.spring.ExkoRefreshEvent
import io.exko.scopedcss.CssBundle
import io.exko.scopedcss.ScopedCssLink
import io.exko.scopedcss.Styled
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import java.security.MessageDigest

private val log = KotlinLogging.logger {}

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfiguration
class ScopedCssAutoConfiguration(
    val eventPublisher: ApplicationEventPublisher,
) {

    @Bean
    fun scopedCssEndpointController() = ScopedCssEndpointController()
}
