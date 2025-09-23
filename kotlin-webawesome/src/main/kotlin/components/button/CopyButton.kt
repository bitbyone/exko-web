package io.exko.webawesome.components.button

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.id

class WA_COPY_BUTTON(consumer: TagConsumer<*>) : HTMLTag(
    "wa-copy-button",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    DisabledAware, ValueAware {

    var from: String
        get() = stringAttr[this, "from"]
        set(value) { stringAttr[this, "from"] = value }

    var copyLabel: String
        get() = stringAttr[this, "copy-label"]
        set(value) { stringAttr[this, "copy-label"] = value }

    var successLabel: String
        get() = stringAttr[this, "success-label"]
        set(value) { stringAttr[this, "success-label"] = value }

    var errorLabel: String
        get() = stringAttr[this, "error-label"]
        set(value) { stringAttr[this, "error-label"] = value }

    var feedbackDuration: Int
        get() = intAttr[this, "feedback-duration"]
        set(value) { intAttr[this, "feedback-duration"] = value }

    var tooltipPlacement: SimplePlacement
        get() = simplePlacementAttr[this, "tooltip-placement"]
        set(value) { simplePlacementAttr[this, "tooltip-placement"] = value }

    fun slotCopyIcon(children: Children) = children.visitWithSlotAttribute("copy-icon")
    fun slotSuccessIcon(children: Children) = children.visitWithSlotAttribute("success-icon")
    fun slotErrorIcon(children: Children) = children.visitWithSlotAttribute("error-icon")
}

fun FlowContent.CopyButton(
    classes: String? = null,
    id: String? = null,
    value: String? = null,
    from: String? = null,
    disabled: Boolean? = null,
    copyLabel: String? = null,
    successLabel: String? = null,
    errorLabel: String? = null,
    feedbackDuration: Int? = null,
    tooltipPlacement: SimplePlacement? = null,
    block: WA_COPY_BUTTON.() -> Unit = {}
) {
    val copy = WA_COPY_BUTTON(consumer)
    copy.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        value?.let { this.value = it }
        from?.let { this.from = it }
        disabled?.let { this.disabled = it }
        copyLabel?.let { this.copyLabel = it }
        successLabel?.let { this.successLabel = it }
        errorLabel?.let { this.errorLabel = it }
        feedbackDuration?.let { this.feedbackDuration = it }
        tooltipPlacement?.let { this.tooltipPlacement = it }
    }
}
