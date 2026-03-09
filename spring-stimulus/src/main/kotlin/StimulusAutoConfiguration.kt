package io.exko.stimulus.spring

import io.exko.stimulus.StimulusController
import io.exko.stimulus.StimulusScript
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean

private val log = KotlinLogging.logger {}

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfiguration
class StimulusAutoConfiguration {

    @Bean
    fun stimulusBundle(controllers: List<StimulusController>): StimulusBundle {
        val bundle = StimulusBundle(controllers)
        StimulusScript.src = bundle.mainPath
        log.info { "Stimulus bundle generated with ${controllers.size} controller(s): ${bundle.mainPath}" }
        return bundle
    }

    @Bean
    fun stimulusEndpointController(bundle: StimulusBundle) = StimulusEndpointController(bundle)
}
