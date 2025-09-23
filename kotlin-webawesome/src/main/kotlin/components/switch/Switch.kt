package io.exko.webawesome.components.switch

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.id

class WA_SWITCH(consumer: TagConsumer<*>) : HTMLTag(
    "wa-switch",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    NameAware, TitleAware, ValueAware, CheckedAware, SizeAware, DisabledAware,
    RequiredAware, WithHintAware, HintAware, FormAware {

    fun slotHint(children: Children) = children.visitWithSlotAttribute("hint")
}

fun FlowContent.Switch(
    classes: String? = null,
    id: String? = null,
    name: String? = null,
    title: String? = null,
    size: Size? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    withHint: Boolean? = null,
    hint: String? = null,
    checked: Boolean? = null,
    value: String? = null,
    form: String? = null,
    block: WA_SWITCH.() -> Unit = {}
) {
    val sw = WA_SWITCH(consumer)
    sw.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        name?.let { this.name = it }
        title?.let { this.title = it }
        size?.let { this.size = it }
        disabled?.let { this.disabled = it }
        required?.let { this.required = it }
        withHint?.let { this.withHint = it }
        hint?.let { this.hint = it }
        checked?.let { this.checked = it }
        value?.let { this.value = it }
        form?.let { this.form = it }
    }
}
