package io.exko.scopedcss.spring

import io.exko.scopedcss.CssBundle
import org.springframework.http.CacheControl
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/__scoped-css")
class ScopedCssEndpointController(private val bundle: CssBundle) {

    @GetMapping("/{filename}.css")
    fun serve(@PathVariable filename: String): ResponseEntity<String> {
        if (filename != "styles-${bundle.hash}") return ResponseEntity.notFound().build()
        return ResponseEntity.ok()
            .contentType(MediaType("text", "css"))
            .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic().immutable())
            .body(bundle.content)
    }
}
