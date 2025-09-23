package io.exko.webawesome.components.tag

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.*

@HtmlTag
class WA_TAG(consumer: TagConsumer<*>) :
    HTMLTag(
        "wa-tag",
        consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
        emptyMap(),
        inlineTag = true,
        emptyTag = false,
    ),
    FlowContent,
    WebComponent,
    CoreAttributeGroupFacade,
    VariantAware,
    AppearanceAware,
    SizeAware,
    PillAware {

    var withRemove: Boolean
        get() = tickerAttr[this, "with-remove"]
        set(value) {
            tickerAttr[this, "with-remove"] = value
        }
}

@UI
@HtmlTagMarker
fun FlowContent.Tag(
    classes: String? = null,
    id: String? = null,
    size: Size? = null,
    variant: Variant? = null,
    appearance: Appearance? = null,
    pill: Boolean? = null,
    withRemove: Boolean? = null,
    block: WA_TAG.() -> Unit = {},
) {
    val tag = WA_TAG(consumer)
    tag.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        size?.let { this.size = it }
        variant?.let { this.variant = it }
        appearance?.let { this.appearance = it }
        pill?.let { this.pill = it }
        withRemove?.let { this.withRemove = it }
    }
}
