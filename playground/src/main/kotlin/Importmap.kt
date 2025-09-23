package io.exko.sandbox.uikit

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

fun HEAD.importMap() {
    script {
        type = "importmap"
        unsafe {
            raw(Importmap.content)
        }
    }
}
