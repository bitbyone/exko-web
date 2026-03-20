package io.exko.scopedcss.spring

import io.exko.scopedcss.CssBundle
import io.exko.scopedcss.StyledBundler
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
class ScopedCssEndpointController {

    private val recentBundles = LinkedHashMap<String, CssBundle>(4, 0.75f, true)

    @GetMapping("/{filename}.css")
    fun serve(@PathVariable filename: String): ResponseEntity<String> {
        val bundle = StyledBundler.bundle()
        cacheBundle(bundle)

        val hash = filename.removePrefix("styles-")
        val matched = recentBundles[hash] ?: bundle

        return ResponseEntity.ok()
            .contentType(MediaType("text", "css"))
            .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic().immutable())
            .body(matched.content)
    }

    private fun cacheBundle(bundle: CssBundle) {
        recentBundles[bundle.hash] = bundle
        while (recentBundles.size > 3) {
            recentBundles.remove(recentBundles.keys.first())
        }
    }
}
