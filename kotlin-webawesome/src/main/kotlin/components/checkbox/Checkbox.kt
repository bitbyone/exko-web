package io.exko.webawesome.components.checkbox

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.*

class WA_CHECKBOX(consumer: TagConsumer<*>) : HTMLTag(
    "wa-checkbox",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    NameAware, TitleAware, ValueAware, SizeAware, DisabledAware, CheckedAware, IndeterminateAware, RequiredAware, HintAware, FormAware {

    fun slotHint(children: Children) = children.visitWithSlotAttribute("hint")
}

fun FlowContent.Checkbox(
    classes: String? = null,
    id: String? = null,
    name: String? = null,
    title: String? = null,
    value: String? = null,
    size: Size? = null,
    disabled: Boolean? = null,
    indeterminate: Boolean? = null,
    checked: Boolean? = null,
    required: Boolean? = null,
    hint: String? = null,
    form: String? = null,
    block: WA_CHECKBOX.() -> Unit = {}
) {
    val checkbox = WA_CHECKBOX(consumer)
    checkbox.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        name?.let { this.name = it }
        title?.let { this.title = it }
        value?.let { this.value = it }
        size?.let { this.size = it }
        disabled?.let { this.disabled = it }
        indeterminate?.let { this.indeterminate = it }
        checked?.let { this.checked = it }
        required?.let { this.required = it }
        hint?.let { this.hint = it }
        form?.let { this.form = it }
    }
}
