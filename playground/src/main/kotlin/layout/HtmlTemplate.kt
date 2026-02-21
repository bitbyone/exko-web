package io.exko.sandbox.uikit.layout

import io.exko.html.Children
import io.exko.html.UI
import io.exko.html.render
import io.exko.htmx.dsl.boost
import io.exko.htmx.dsl.hx
import io.exko.sandbox.uikit.importMap
import io.exko.spring.hotswap.HotSwapAgentUtils.isLivereloadServerStarted
import kotlinx.html.*
import org.intellij.lang.annotations.Language

@UI
fun htmlTemplate(content: Children) = render {
    html {
        lang = "en"
        classes = setOf("wa-cloak wa-palette-bright wa-brand-blue")
        head {
            importMap()
            defineJs()
            defineCss()
            title("Exko Playground")
        }
        body {
            hx { boost(enabled = true, inherited = true) }
            content()
        }
    }
}

@UI
private fun HEAD.defineCss() {
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

@UI
fun SCRIPT.src(
    @Language("HTML", prefix = "<script src='", suffix = "'></script>")
    url: String,
) {
    src = url
}

@UI
private fun HEAD.defineJs() {
    script {
        src("https://cdn.jsdelivr.net/npm/htmx.org@4.0.0-alpha7/dist/htmx.min.js")
    }
    script {
        src("https://kit.webawesome.com/66a00c94d1fd4b3b.js")
        crossorigin = ScriptCrossorigin.anonymous
    }
    script {
        defer = true
        type = "module"
        src = "/js/index.mjs"
    }
    script {
        defer = true
        type = "module"
        src = "/js/stimulus.js"
    }
//    meta {
//        name = "htmx-config"
//        content = """{"includeIndicatorStyles": true, "scrollBehavior": "smooth", "globalViewTransitions": false}"""
//    }
    if (isLivereloadServerStarted.get() || true) {
        script {
//            src("http://localhost:35729/livereload.js?maxdelay=1000")
//            src("https://cdn.jsdelivr.net/npm/livereload-morph@latest/dist/livereload-morph.min.js?host=localhost&verbose=true&maxdelay=1000")
//            defer = true
            type = "module"
            src = "/js/livemorph.js?host=localhost&verbose=true&maxdelay=1000"
        }
    }
}
