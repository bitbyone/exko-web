package io.exko.webawesome.components.icon

import io.exko.html.WebComponent
import io.exko.html.WebComponentsTagConsumer
import io.exko.html.classes
import io.exko.html.orNewIfNotAlready
import io.exko.html.visit
import io.exko.webawesome.props.LabelAware
import io.exko.webawesome.props.NameAware
import io.exko.webawesome.props.stringAttr
import io.exko.webawesome.props.tickerAttr
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.CoreAttributeGroupFacade
import kotlinx.html.id

/**
 * Kotlin wrapper for <wa-icon> web component.
 * Attributes mirrored from web-types.json for v3.0.0-beta6
 */
class WA_ICON(consumer: TagConsumer<*>) : HTMLTag(
    "wa-icon",
    consumer.orNewIfNotAlready(::WebComponentsTagConsumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent, WebComponent, CoreAttributeGroupFacade, NameAware, LabelAware {

    /** The family of icons to choose from (e.g., classic, sharp, duotone, brands). */
    var family: String
        get() = stringAttr[this, "family"]
        set(value) {
            stringAttr[this, "family"] = value
        }

    /** The variant of the icon (e.g., thin, light, regular, solid). */
    var variant: String
        get() = stringAttr[this, "variant"]
        set(value) {
            stringAttr[this, "variant"] = value
        }

    /** Sets the width of the icon to match the cropped SVG viewBox (Font Awesome auto-width). */
    var autoWidth: Boolean
        get() = tickerAttr[this, "auto-width"]
        set(value) {
            tickerAttr[this, "auto-width"] = value
        }

    /** Swaps the opacity of duotone icons. */
    var swapOpacity: Boolean
        get() = tickerAttr[this, "swap-opacity"]
        set(value) {
            tickerAttr[this, "swap-opacity"] = value
        }

    /** External URL of an SVG file. */
    var src: String
        get() = stringAttr[this, "src"]
        set(value) {
            stringAttr[this, "src"] = value
        }

    /** Name of a registered custom icon library. */
    var library: String
        get() = stringAttr[this, "library"]
        set(value) {
            stringAttr[this, "library"] = value
        }
}

fun FlowContent.Icon(
    classes: String? = null,
    id: String? = null,
    name: String? = null,
    family: String? = null,
    variant: String? = null,
    autoWidth: Boolean? = null,
    swapOpacity: Boolean? = null,
    src: String? = null,
    label: String? = null,
    library: String? = null,
    block: WA_ICON.() -> Unit = {}
) {
    val icon = WA_ICON(consumer)
    icon.visit(block) {
        classes?.let { classes(it) }
        id?.let { this.id = it }
        name?.let { this.name = it }
        family?.let { this.family = it }
        variant?.let { this.variant = it }
        autoWidth?.let { this.autoWidth = it }
        swapOpacity?.let { this.swapOpacity = it }
        src?.let { this.src = it }
        label?.let { this.label = it }
        library?.let { this.library = it }
    }
}
