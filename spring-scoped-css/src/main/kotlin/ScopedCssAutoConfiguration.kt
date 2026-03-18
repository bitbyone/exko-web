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

//    @Bean
    fun scopedCssBundle(styledSheets: List<Styled>): CssBundle {
        styledSheets.forEach { it.reload() }
        val content = styledSheets
            .joinToString("\n\n") { it.renderCss() }
        val hash = sha256(content).take(8)
        val bundle = CssBundle(
            content = content,
            hash = hash,
            path = "/__scoped-css/styles-$hash.css",
        )
        ScopedCssLink.href = bundle.path
        log.info { "Scoped CSS bundle generated with ${styledSheets.size} stylesheet(s): ${bundle.path}" }
        eventPublisher.publishEvent(ExkoRefreshEvent(styledSheets))
        println(bundle)
        return bundle
    }

//    @Bean
    fun scopedCssEndpointController(bundle: CssBundle) = ScopedCssEndpointController(bundle)

    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
    }
}
