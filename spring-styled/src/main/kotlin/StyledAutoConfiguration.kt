package io.exko.styled.spring

import io.exko.htmx.spring.ExkoRefreshEvent
import io.exko.styled.CssBundle
import io.exko.styled.StyledLink
import io.exko.styled.Styled
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import java.security.MessageDigest

private val log = KotlinLogging.logger {}

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfiguration
class StyledAutoConfiguration {

    @Bean
    fun styledEndpointController() = StyledEndpointController()
}
