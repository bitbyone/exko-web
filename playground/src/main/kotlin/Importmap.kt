package io.exko.sandbox.uikit

import io.exko.html.UI
import kotlinx.html.HEAD
import kotlinx.html.script
import kotlinx.html.unsafe
import org.springframework.core.io.ClassPathResource

object Importmap {
    val content by lazy {
        ClassPathResource("importmap.json").inputStream.use {
            it.bufferedReader().readText()
        }
    }
}

@UI
fun HEAD.importMap() {
    script {
        type = "importmap"
        unsafe {
            raw(Importmap.content)
        }
    }
}
