package io.exko.webawesome.components.textarea

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_TEXTAREA(consumer: TagConsumer<*>) : HTMLTag(
    "wa-textarea",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    SizeAware, SimpleAppearanceAware, LabelAware, HintAware, WithLabelAware, WithHintAware, ValueAware, FormAware,
    NameAware, TitleAware, PlaceholderAware, DisabledAware, ReadonlyAware, RequiredAware, MinLengthAware, MaxLengthAware, AutocorrectAware,
    AutofocusAware, EnterKeyHintAware, SpellcheckAware, InputModeAware, AutocompleteAware, AutocapitalizeAware, ResizeAware {

    var rows: Int
        get() = intAttr[this, "rows"]
        set(value) { intAttr[this, "rows"] = value }

    // Slots
    fun slotLabel(children: Children) = children.visitWithSlotAttribute("label")
    fun slotHint(children: Children) = children.visitWithSlotAttribute("hint")
}

fun FlowContent.Textarea(
    classes: String? = null,
    id: String? = null,
    title: String? = null,
    size: Size? = null,
    appearance: SimpleAppearance? = null,
    label: String? = null,
    hint: String? = null,
    withLabel: Boolean? = null,
    withHint: Boolean? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    readonly: Boolean? = null,
    required: Boolean? = null,
    autofocus: Boolean? = null,
    enterkeyhint: EnterKeyHint? = null,
    spellcheck: Boolean? = null,
    inputmode: InputMode? = null,
    autocomplete: String? = null,
    autocapitalize: AutoCapitalize? = null,
    autocorrect: AutoCorrect? = null,
    rows: Int? = null,
    resize: Resize? = null,
    minlength: Int? = null,
    maxlength: Int? = null,
    value: String? = null,
    form: String? = null,
    block: WA_TEXTAREA.() -> Unit = {}
) {
    val textarea = WA_TEXTAREA(consumer)
    textarea.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        title?.let { this.title = it }
        size?.let { this.size = it }
        appearance?.let { this.appearance = it }
        label?.let { this.label = it }
        hint?.let { this.hint = it }
        withLabel?.let { this.withLabel = it }
        withHint?.let { this.withHint = it }
        name?.let { this.name = it }
        placeholder?.let { this.placeholder = it }
        disabled?.let { this.disabled = it }
        readonly?.let { this.readonly = it }
        required?.let { this.required = it }
        autofocus?.let { this.autofocus = it }
        enterkeyhint?.let { this.enterkeyhint = it }
        spellcheck?.let { this.spellcheck = it }
        inputmode?.let { this.inputmode = it }
        autocomplete?.let { this.autocomplete = it }
        autocapitalize?.let { this.autocapitalize = it }
        autocorrect?.let { this.autocorrect = it }
        rows?.let { this.rows = it }
        resize?.let { this.resize = it }
        minlength?.let { this.minlength = it }
        maxlength?.let { this.maxlength = it }
        value?.let { this.value = it }
        form?.let { this.form = it }
    }
}
