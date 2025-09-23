package io.exko.webawesome.components.radio

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_RADIO(consumer: TagConsumer<*>) : HTMLTag(
    "wa-radio",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    ValueAware, SizeAware, DisabledAware, FormAware, RadioAppearanceAware

fun FlowContent.Radio(
    classes: String? = null,
    id: String? = null,
    value: String? = null,
    size: Size? = null,
    disabled: Boolean? = null,
    form: String? = null,
    appearance: RadioAppearance? = null,
    block: WA_RADIO.() -> Unit = {}
) {
    val radio = WA_RADIO(consumer)
    radio.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        value?.let { this.value = it }
        size?.let { this.size = it }
        disabled?.let { this.disabled = it }
        form?.let { this.form = it }
        appearance?.let { this.appearance = it }
    }
}
