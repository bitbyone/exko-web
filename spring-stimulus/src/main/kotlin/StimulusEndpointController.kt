package io.exko.stimulus.spring

import org.springframework.http.CacheControl
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/__stimulus")
class StimulusEndpointController(private val bundle: StimulusBundle) {

    @GetMapping("/{filename}.js")
    fun serve(@PathVariable filename: String): ResponseEntity<String> {
        val content = bundle.fileMap[filename] ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok()
            .contentType(MediaType("application", "javascript"))
            .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic().immutable())
            .body(content)
    }
}
