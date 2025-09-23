package io.exko.html

import kotlinx.html.*

inline fun FlowContent.tag(
    name: String,
    vararg attrs: Pair<String, String>,
    crossinline block: ButtonServerCommonFlowInteractivePhrasingGroupFacadeAttributeContent.() -> Unit = {}
) =
    object :
        HTMLTag(
            name,
            consumer,
            attrs.toMap(),
            null,
            false,
            false
        ),
        ButtonServerCommonFlowInteractivePhrasingGroupFacadeAttributeContent {
    }.visit(block)
