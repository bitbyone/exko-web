package io.exko.webawesome.components.avatar

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.id

class WA_AVATAR(consumer: TagConsumer<*>) : HTMLTag(
    "wa-avatar",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, ImageAware, LabelAware, InitialsAware, ImageLoadingAware, ShapeAware {

    fun slotIcon(children: Children) = children.visitWithSlotAttribute("icon")
}

fun FlowContent.Avatar(
    classes: String? = null,
    id: String? = null,
    image: String? = null,
    label: String? = null,
    initials: String? = null,
    loading: ImageLoading? = null,
    shape: Shape? = null,
    block: WA_AVATAR.() -> Unit = {}
) {
    val avatar = WA_AVATAR(consumer)
    avatar.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        image?.let { this.image = it }
        label?.let { this.label = it }
        initials?.let { this.initials = it }
        loading?.let { this.loading = it }
        shape?.let { this.shape = it }
    }
}
