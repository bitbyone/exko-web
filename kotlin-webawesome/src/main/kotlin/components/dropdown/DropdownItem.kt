package io.exko.webawesome.components.dropdown

import io.exko.html.*
import io.exko.webawesome.components.dropdown.attr.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_DROPDOWN_ITEM(consumer: TagConsumer<*>) : HTMLTag(
    "wa-dropdown-item",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    DropdownItemVariantAware,
    ValueAware,
    DropdownItemTypeAware,
    CheckedAware,
    DisabledAware,
    SubmenuOpenAware {

    fun slotIcon(children: Children) = children.visitWithSlotAttribute("icon")
    fun slotDetails(children: Children) = children.visitWithSlotAttribute("details")
    fun slotSubmenu(children: Children) = children.visitWithSlotAttribute("submenu")
}

fun FlowContent.DropdownItem(
    classes: String? = null,
    id: String? = null,
    variant: DropdownItemVariant? = null,
    value: String? = null,
    type: DropdownItemType? = null,
    checked: Boolean? = null,
    disabled: Boolean? = null,
    submenuOpen: Boolean? = null,
    block: WA_DROPDOWN_ITEM.() -> Unit = {}
) {
    val item = WA_DROPDOWN_ITEM(consumer)
    item.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        variant?.let { this.variant = it }
        value?.let { this.value = it }
        type?.let { this.type = it }
        checked?.let { this.checked = it }
        disabled?.let { this.disabled = it }
        submenuOpen?.let { this.submenuOpen = it }
    }
}
