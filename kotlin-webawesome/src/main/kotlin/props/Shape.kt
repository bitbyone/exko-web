package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

enum class Shape(override val realValue: String) : AttributeEnum {
    circle("circle"),
    square("square"),
    rounded("rounded");

    companion object {
        val valuesMap = Shape.entries.associateBy { it.realValue }
    }
}

interface ShapeAware : Tag {

    companion object {
        const val ATTR_NAME = "shape"
    }

    var shape: Shape
        get() = shapeAttr[this, ATTR_NAME]
        set(value) {
            shapeAttr[this, ATTR_NAME] = value
        }
}

internal val shapeAttr = EnumAttribute(Shape.valuesMap)
