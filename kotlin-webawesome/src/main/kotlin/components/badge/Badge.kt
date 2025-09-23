package io.exko.webawesome.components.badge

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.HtmlTagMarker
import kotlinx.html.TagConsumer
import kotlinx.html.id

@HtmlTag
class WA_BADGE(consumer: TagConsumer<*>) :
    HTMLTag(
        "wa-badge",
        consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
        emptyMap(),
        inlineTag = false,
        emptyTag = false,
    ),
    FlowContent,
    WebComponent,
    CoreAttributeGroupFacade,
    VariantAware,
    AppearanceAware,
    PillAware,
    AttentionAware

@UI
@HtmlTagMarker
fun FlowContent.Badge(
    classes: String? = null,
    id: String? = null,
    variant: Variant? = null,
    appearance: Appearance? = null,
    pill: Boolean? = null,
    attention: Attention? = null,
    block: WA_BADGE.() -> Unit = {},
) {
    val badge = WA_BADGE(consumer)
    badge.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        variant?.let { this.variant = it }
        appearance?.let { this.appearance = it }
        pill?.let { this.pill = it }
        attention?.let { this.attention = it }
    }
}
