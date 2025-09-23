package io.exko.webawesome.components.tabs

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

class WA_TAB_GROUP(consumer: TagConsumer<*>) : HTMLTag(
    "wa-tab-group",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    TabsPlacementAware,
    ActivationAware,
    WithoutScrollControlsAware {

    var active: String
        get() = attributes[ACTIVE_ATTR] ?: ""
        set(value) {
            attributes[ACTIVE_ATTR] = value
        }

    fun slotNav(children: Children) = children.visitWithSlotAttribute("nav")

    companion object {
        private const val ACTIVE_ATTR = "active"
    }
}

fun FlowContent.TabGroup(
    classes: String? = null,
    id: String? = null,
    active: String? = null,
    placement: TabsPlacement? = null,
    activation: Activation? = null,
    withoutScrollControls: Boolean? = null,
    block: WA_TAB_GROUP.() -> Unit = {}
) {
    val tg = WA_TAB_GROUP(consumer)
    tg.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        active?.let { this.active = it }
        placement?.let { this.placement = it }
        activation?.let { this.activation = it }
        withoutScrollControls?.let { this.withoutScrollControls = it }
    }
}
