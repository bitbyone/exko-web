package io.exko.webawesome.components.fileinput

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.HtmlTagMarker
import kotlinx.html.TagConsumer

@HtmlTag
class WA_FILE_INPUT(consumer: TagConsumer<*>) :
    HTMLTag(
        "wa-file-input",
        consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
        emptyMap(),
        inlineTag = false,
        emptyTag = false,
    ),
    FlowContent,
    WebComponent,
    CoreAttributeGroupFacade,
    AcceptAware,
    DisabledAware,
    HintAware,
    LabelAware,
    MultipleAware,
    NameAware,
    RequiredAware,
    SizeAware,
    WithLabelAware,
    WithHintAware {

    fun slotDropzone(children: Children) = children.visitWithSlotAttribute("dropzone")
    fun slotFileIcon(children: Children) = children.visitWithSlotAttribute("file-icon")
    fun slotHint(children: Children) = children.visitWithSlotAttribute("hint")
    fun slotLabel(children: Children) = children.visitWithSlotAttribute("label")
}

@UI
@HtmlTagMarker
fun FlowContent.FileInput(
    classes: String? = null,
    name: String? = null,
    label: String? = null,
    hint: String? = null,
    accept: String? = null,
    multiple: Boolean? = null,
    required: Boolean? = null,
    disabled: Boolean? = null,
    size: Size? = null,
    withLabel: Boolean? = null,
    withHint: Boolean? = null,
    block: WA_FILE_INPUT.() -> Unit = {},
) {
    val fileInput = WA_FILE_INPUT(consumer)
    fileInput.visit(block) {
        classes?.let { classes(it) }
        name?.let { this.name = it }
        label?.let { this.label = it }
        hint?.let { this.hint = it }
        accept?.let { this.accept = it }
        multiple?.let { this.multiple = it }
        required?.let { this.required = it }
        disabled?.let { this.disabled = it }
        size?.let { this.size = it }
        withLabel?.let { this.withLabel = it }
        withHint?.let { this.withHint = it }
    }
}
