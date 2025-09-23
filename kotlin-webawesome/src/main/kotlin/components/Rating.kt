package io.exko.webawesome.components

import io.exko.html.*
import io.exko.webawesome.props.*
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.id

class WA_RATING(consumer: TagConsumer<*>) : HTMLTag(
    "wa-rating",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade,
    LabelAware, ReadonlyAware, DisabledAware, SizeAware {

    var value: Int
        get() = intAttr[this, "value"]
        set(value) { intAttr[this, "value"] = value }

    var max: Int
        get() = intAttr[this, "max"]
        set(value) { intAttr[this, "max"] = value }

    var precision: Double
        get() = doubleAttr[this, "precision"]
        set(value) { doubleAttr[this, "precision"] = value }
}

fun FlowContent.Rating(
    classes: String? = null,
    id: String? = null,
    label: String? = null,
    value: Int? = null,
    max: Int? = null,
    precision: Double? = null,
    readonly: Boolean? = null,
    disabled: Boolean? = null,
    size: Size? = null,
    block: WA_RATING.() -> Unit = {}
) {
    val rating = WA_RATING(consumer)
    rating.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        label?.let { this.label = it }
        value?.let { this.value = it }
        max?.let { this.max = it }
        precision?.let { this.precision = it }
        readonly?.let { this.readonly = it }
        disabled?.let { this.disabled = it }
        size?.let { this.size = it }
    }
}
