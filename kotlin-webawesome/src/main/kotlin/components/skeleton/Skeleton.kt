package io.exko.webawesome.components.skeleton

import io.exko.html.*
import io.exko.webawesome.props.SkeletonEffect
import io.exko.webawesome.props.SkeletonEffectAware
import kotlinx.html.*

class WA_SKELETON(consumer: TagConsumer<*>) : HTMLTag(
    "wa-skeleton",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, SkeletonEffectAware

fun FlowContent.Skeleton(
    classes: String? = null,
    id: String? = null,
    effect: SkeletonEffect? = null,
    block: WA_SKELETON.() -> Unit = {}
) {
    val skeleton = WA_SKELETON(consumer)
    skeleton.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        effect?.let { this.effect = it }
    }
}
