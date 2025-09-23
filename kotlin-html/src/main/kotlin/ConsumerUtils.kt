package io.exko.html

import kotlinx.html.Tag
import kotlinx.html.TagConsumer

inline fun <reified T : TagConsumer<*>> TagConsumer<*>.orNewIfNotAlready(instance: (TagConsumer<*>) -> T): T {
    return this as? T ?: instance(this)
}

inline fun <T : Tag> T.visit(block: T.() -> Unit, initAttributes: T.() -> Unit) {
    consumer.onTagStart(this)
    initAttributes()
    this.block()
    consumer.onTagEnd(this)
}
