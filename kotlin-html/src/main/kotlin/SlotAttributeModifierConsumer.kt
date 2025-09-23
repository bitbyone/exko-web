package io.exko.html

import io.exko.html.SlotAttributeModifierWrapperTag.Companion.TAG_NAME
import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.visit

class SlotAttributeModifierConsumer<T>(
    private val slot: String,
    private val delegate: TagConsumer<T>
) : TagConsumer<T> by delegate {

    private var applied = false

    // TODO this thing could be reused for children elements as well, no need to instantiate for every slot
    override fun onTagStart(tag: Tag) {
        if (tag.tagName != TAG_NAME) {
            delegate.onTagStart(tag)
            if (!applied) {
                tag.attributes["slot"] = slot
                applied = true
            }
        }
    }

    override fun onTagEnd(tag: Tag) {
        if (tag.tagName != TAG_NAME) {
            delegate.onTagEnd(tag)
        }
    }
}

class SlotAttributeModifierWrapperTag(slot: String, consumer: TagConsumer<*>) : HTMLTag(
    TAG_NAME,
    SlotAttributeModifierConsumer(slot, consumer),
    emptyMap(),
    inlineTag = false,
    emptyTag = false
), FlowContent {

    companion object {
        const val TAG_NAME = "__slot"
    }
}

context(tag: HTMLTag)
fun Children.visitWithSlotAttribute(name: String) {
    SlotAttributeModifierWrapperTag(name, tag.consumer).visit(this)
}
