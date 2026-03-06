package io.exko.htmx.tag

import io.exko.html.visit
import kotlinx.html.CoreAttributeGroupFacadeFlowMetaDataPhrasingContent
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.attributes.StringAttribute

class HX_PARTIAL(consumer: TagConsumer<*>) : HTMLTag(
    "hx-partial",
    consumer,
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent {

    var swap: String
        get() = stringAttr[this, "hx-swap"]
        set(value) {
            stringAttr[this, "hx-swap"] = value
        }

    var target: String
        get() = stringAttr[this, "hx-target"]
        set(value) {
            stringAttr[this, "hx-target"] = value
        }
}

fun FlowContent.hxPartial(
    swap: String? = null,
    target: String? = null,
    block: HX_PARTIAL.() -> Unit = {}
) {
    val hxPartial = HX_PARTIAL(consumer)
    hxPartial.visit(block) {
        swap?.let { this.swap = it }
        target?.let { this.target = it }
    }
}

internal val stringAttr: StringAttribute = StringAttribute()

class SKIP(consumer: TagConsumer<*>) : HTMLTag(
    "SKIP",
    consumer,
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, CoreAttributeGroupFacadeFlowMetaDataPhrasingContent {
}

fun TagConsumer<*>.skip(
    block: SKIP.() -> Unit = {}
) {
    val skip = SKIP(this)
    skip.visit(block) {
    }
}
