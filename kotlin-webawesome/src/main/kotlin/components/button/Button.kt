package io.exko.webawesome.components.button

import io.exko.html.*
import io.exko.webawesome.props.*
import io.exko.webawesome.props.Target
import kotlinx.html.*
import kotlinx.html.id

@HtmlTag
class WA_BUTTON(consumer: TagConsumer<*>) :
    HTMLTag(
        "wa-button",
        consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
        emptyMap(),
        inlineTag = false,
        emptyTag = false,
    ),
    ButtonServerCommonFlowInteractivePhrasingGroupFacadeAttributeContent,
    WebComponent,
    CoreAttributeGroupFacade,
    VariantAware,
    AppearanceAware,
    SizeAware,
    DisabledAware,
    LoadingAware,
    ButtonTypeAware,
    HrefAware,
    TargetAware,
    RelAware,
    DownloadAware,
    NameAware,
    TitleAware,
    ValueAware,
    WithCaretAware,
    PillAware,
    FormAware,
    FormActionAware,
    FormEnctypeAware,
    FormMethodAware,
    FormNoValidateAware,
    FormTargetAware {

    fun slotStart(children: Children) = children.visitWithSlotAttribute("start")
    fun slotEnd(children: Children) = children.visitWithSlotAttribute("end")
}

@UI
@HtmlTagMarker
fun FlowContent.Button(
    classes: String? = null,
    id: String? = null,
    name: String? = null,
    title: String? = null,
    variant: Variant? = null,
    appearance: Appearance? = null,
    size: Size? = null,
    disabled: Boolean? = null,
    loading: Boolean? = null,
    type: ButtonType? = null,
    href: String? = null,
    target: Target? = null,
    rel: String? = null,
    download: String? = null,
    value: String? = null,
    withCaret: Boolean? = null,
    pill: Boolean? = null,
    form: String? = null,
    formAction: String? = null,
    formEnctype: FormEncType? = null,
    formMethod: FormMethod? = null,
    formNoValidate: Boolean? = null,
    formTarget: String? = null,
    block: WA_BUTTON.() -> Unit = {},
) {
    val button = WA_BUTTON(consumer)
    button.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        name?.let { this.name = it }
        title?.let { this.title = it }
        variant?.let { this.variant = it }
        appearance?.let { this.appearance = it }
        size?.let { this.size = it }
        disabled?.let { this.disabled = it }
        loading?.let { this.loading = it }
        type?.let { this.type = it }
        href?.let { this.href = it }
        target?.let { this.target = it }
        rel?.let { this.rel = it }
        download?.let { this.download = it }
        value?.let { this.value = it }
        withCaret?.let { this.withCaret = it }
        pill?.let { this.pill = it }
        form?.let { this.form = it }
        formAction?.let { this.formAction = it }
        formEnctype?.let { this.formEnctype = it }
        formMethod?.let { this.formMethod = it }
        formNoValidate?.let { this.formNoValidate = it }
        formTarget?.let { this.formTarget = it }
    }
}
