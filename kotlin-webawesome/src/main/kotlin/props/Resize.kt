package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Textarea resize supports 'none' | 'vertical' | 'horizontal' | 'both' | 'auto' per wa-textarea.d.ts
 */
enum class Resize(override val realValue: String) : AttributeEnum {
    none("none"),
    vertical("vertical"),
    horizontal("horizontal"),
    both("both"),
    auto("auto");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val resizeAttr = EnumAttribute(Resize.valuesMap)

interface ResizeAware : Tag {
    companion object {
        const val ATTR_NAME = "resize"
    }

    var resize: Resize
        get() = resizeAttr[this, ATTR_NAME]
        set(value) {
            resizeAttr[this, ATTR_NAME] = value
        }
}
