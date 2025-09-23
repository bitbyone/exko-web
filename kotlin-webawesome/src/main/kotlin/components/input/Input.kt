package io.exko.webawesome.components.input

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.HtmlTagMarker
import kotlinx.html.TagConsumer

@HtmlTag
class WA_INPUT(consumer: TagConsumer<*>) :
    HTMLTag(
        "wa-input",
        consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
        emptyMap(),
        inlineTag = false,
        emptyTag = false,
    ),
    FlowContent,
    WebComponent,
    CoreAttributeGroupFacade,
    SizeAware,
    AppearanceAware,
    PillAware,
    LabelAware,
    HintAware,
    WithClearAware,
    PlaceholderAware,
    ReadonlyAware,
    PasswordToggleAware,
    PasswordVisibleAware,
    WithoutSpinButtonsAware,
    NameAware,
    TitleAware,
    ValueAware,
    FormAware,
    RequiredAware,
    InputTypeAware,
    PatternAware,
    MinLengthAware,
    MaxLengthAware,
    MinAware,
    MaxAware,
    StepAware,
    AutocapitalizeAware,
    AutocorrectAware,
    AutocompleteAware,
    AutofocusAware,
    EnterKeyHintAware,
    SpellcheckAware,
    InputModeAware,
    WithLabelAware,
    WithHintAware {

    fun slotLabel(children: Children) = children.visitWithSlotAttribute("label")
    fun slotStart(children: Children) = children.visitWithSlotAttribute("start")
    fun slotEnd(children: Children) = children.visitWithSlotAttribute("end")
    fun slotClearIcon(children: Children) = children.visitWithSlotAttribute("clear-icon")
    fun slotShowPasswordIcon(children: Children) = children.visitWithSlotAttribute("show-password-icon")
    fun slotHidePasswordIcon(children: Children) = children.visitWithSlotAttribute("hide-password-icon")
    fun slotHint(children: Children) = children.visitWithSlotAttribute("hint")
}

@UI
@HtmlTagMarker
fun FlowContent.Input(
    classes: String? = null,
    type: InputType? = null,
    value: String? = null,
    name: String? = null,
    title: String? = null,
    size: Size? = null,
    appearance: Appearance? = null,
    pill: Boolean? = null,
    label: String? = null,
    hint: String? = null,
    withClear: Boolean? = null,
    placeholder: String? = null,
    readonly: Boolean? = null,
    pattern: String? = null,
    minlength: Int? = null,
    maxlength: Int? = null,
    min: String? = null,
    max: String? = null,
    step: String? = null,
    autocapitalize: AutoCapitalize? = null,
    autocorrect: AutoCorrect? = null,
    autocomplete: String? = null,
    autofocus: Boolean? = null,
    enterkeyhint: EnterKeyHint? = null,
    spellcheck: Boolean? = null,
    inputmode: InputMode? = null,
    passwordToggle: Boolean? = null,
    passwordVisible: Boolean? = null,
    withoutSpinButtons: Boolean? = null,
    form: String? = null,
    required: Boolean? = null,
    withLabel: Boolean? = null,
    withHint: Boolean? = null,
    block: WA_INPUT.() -> Unit = {},
) {
    val input = WA_INPUT(consumer)
    input.visit(block) {
        classes?.let { classes(it) }
        type?.let { this.type = it }
        value?.let { this.value = it }
        name?.let { this.name = it }
        title?.let { this.title = it }
        size?.let { this.size = it }
        appearance?.let { this.appearance = it }
        pill?.let { this.pill = it }
        label?.let { this.label = it }
        hint?.let { this.hint = it }
        withClear?.let { this.withClear = it }
        placeholder?.let { this.placeholder = it }
        readonly?.let { this.readonly = it }
        pattern?.let { this.pattern = it }
        minlength?.let { this.minlength = it }
        maxlength?.let { this.maxlength = it }
        min?.let { this.min = it }
        max?.let { this.max = it }
        step?.let { this.step = it }
        autocapitalize?.let { this.autocapitalize = it }
        autocorrect?.let { this.autocorrect = it }
        autocomplete?.let { this.autocomplete = it }
        autofocus?.let { this.autofocus = it }
        enterkeyhint?.let { this.enterkeyhint = it }
        spellcheck?.let { this.spellcheck = it }
        inputmode?.let { this.inputmode = it }
        passwordToggle?.let { this.passwordToggle = it }
        passwordVisible?.let { this.passwordVisible = it }
        withoutSpinButtons?.let { this.withoutSpinButtons = it }
        form?.let { this.form = it }
        required?.let { this.required = it }
        withLabel?.let { this.withLabel = it }
        withHint?.let { this.withHint = it }
    }
}
