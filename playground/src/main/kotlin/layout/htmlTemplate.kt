package io.exko.sandbox.uikit.layout

import io.exko.html.Children
import io.exko.html.render
import io.exko.sandbox.uikit.importMap
import io.exko.spring.hotswap.HotSwapAgentUtils.isLivereloadServerStarted
import kotlinx.html.*
import org.intellij.lang.annotations.Language

fun htmlTemplate(
    content: Children
) = render {
    html {
        lang = "en"
        classes = setOf("wa-cloak wa-theme-tailspin wa-palette-bright wa-brand-blue")
        head {
            importMap()
            defineJs()
            defineCss()
            title("Exko Playground")
        }
        body {
            attributes["hx-boost"] = "true"
            content()
        }
    }
}

private fun HEAD.defineCss() {
    link {
        rel = "stylesheet"
        href = "/vendor/wa-3.0.0-beta6/dist-cdn/styles/webawesome.css"
    }
    link {
        rel = "stylesheet"
        href = "/vendor/wa-3.0.0-beta6/dist-cdn/styles/themes/tailspin.css"
    }
    link {
        rel = "stylesheet"
        href = "/css/custom.css"
    }
    link {
        rel = "stylesheet"
        href = "/css/reset.css"
    }
    meta {
        name = "viewport"
        content = "width=device-width, initial-scale=1"
    }
}

fun SCRIPT.src(
    @Language("HTML", prefix = "<script src='", suffix = "'></script>")
    url: String
) {
    src = url
}

private fun HEAD.defineJs() {
    script {
        src("https://unpkg.com/htmx.org@2.0.7")
    }
    script {
        src("https://unpkg.com/htmx-ext-sse@2.2.2/sse.js")
    }
    script {
        src("/vendor/wa-3.0.0-beta6/dist-cdn/webawesome.loader.js")
        type = "module"
    }
    script {
        defer = true
        type = "module"
        src = "/js/index.mjs"
    }
    meta {
        name = "htmx-config"
        content = """{"includeIndicatorStyles": true, "scrollBehavior": "smooth", "globalViewTransitions": false}"""
    }
    if (isLivereloadServerStarted.get()) {
        script {
            src("http://localhost:35729/livereload.js?maxdelay=1000")
        }
    }
}
