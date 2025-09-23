package io.exko.sandbox.uikit.layout

import io.exko.html.Children
import io.exko.html.UI
import io.exko.html.css
import io.exko.html.raw
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.style

@UI
fun ErrorLayout(children: Children) = htmlTemplate {
    style {
        raw css """
           body {
               background-color: var(--wa-color-red-50);
               display: flex;
               align-items: center;
               justify-content: center;
               filter: brightness(120%);
               --wa-color-shadow: var(--wa-color-gray-10);
           }
           .error-view {
               color: var(--wa-color-danger-on-loud);
               border: 1px solid var(--wa-color-danger-border-loud);
               padding: 2em;
               border-radius: 0.5em;
               background-color: var(--wa-color-red-40);
               box-shadow: 10px 12px 51px -14px var(--wa-color-shadow);
               -webkit-box-shadow: 10px 12px 51px -14px var(--wa-color-shadow);
               -moz-box-shadow: 10px 12px 51px -14px var(--wa-color-shadow);
               max-width: 80%;
           }
        """.trimIndent()
    }
    div("error-view") {
        h1 { +"Error" }
        children()
    }
}
