package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Skeleton effect supports only "pulse", "sheen" and "none" according to wa-skeleton.d.ts
 */
enum class SkeletonEffect(override val realValue: String) : AttributeEnum {
    pulse("pulse"),
    sheen("sheen"),
    none("none");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val skeletonEffectAttr = EnumAttribute(SkeletonEffect.valuesMap)

interface SkeletonEffectAware : Tag {
    companion object {
        const val ATTR_NAME = "effect"
    }

    var effect: SkeletonEffect
        get() = skeletonEffectAttr[this, ATTR_NAME]
        set(value) {
            skeletonEffectAttr[this, ATTR_NAME] = value
        }
}
