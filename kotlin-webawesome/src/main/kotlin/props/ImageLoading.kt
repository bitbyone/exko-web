package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class ImageLoading(override val realValue: String) : AttributeEnum {
    eager("eager"),
    lazy("lazy");

    companion object {
        val valuesMap = ImageLoading.entries.associateBy { it.realValue }
    }
}

interface ImageLoadingAware : Tag {

    companion object {
        const val ATTR_NAME = "loading"
    }

    var loading: ImageLoading
        get() = imageLoadingAttr[this, ATTR_NAME]
        set(value) {
            imageLoadingAttr[this, ATTR_NAME] = value
        }
}

internal val imageLoadingAttr = EnumAttribute(ImageLoading.valuesMap)
