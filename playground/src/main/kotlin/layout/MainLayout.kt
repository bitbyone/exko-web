package io.exko.sandbox.uikit.layout

import io.exko.html.Children
import io.exko.html.UI
import io.exko.htmx.HxSpecs
import kotlinx.html.div
import kotlinx.html.id
import org.intellij.lang.annotations.Language

@UI
fun MainLayout(content: Children) = htmlTemplate {
    div {
        id = PageContentSpecs.id
        content()
    }
}

object PageContentSpecs : HxSpecs {

    override val id = "page-content"

    @Language("css")
    override val target = "#page-content"

    // use enum
    override val swap = "innerHTML show:top showTarget:window"
}
