package io.exko.webawesome.props

import kotlinx.html.attributes.Attribute
import kotlinx.html.attributes.AttributeEncoder
import kotlinx.html.attributes.StringAttribute
import kotlinx.html.attributes.TickerAttribute

internal val tickerAttr: TickerAttribute = TickerAttribute()
internal val stringAttr: StringAttribute = StringAttribute()
internal val intAttr: IntAttribute = IntAttribute()

internal class IntAttribute : Attribute<Int>(IntEncoder)

internal object IntEncoder : AttributeEncoder<Int> {
    override fun encode(attributeName: String, value: Int): String = value.toString()
    override fun decode(attributeName: String, value: String): Int = value.toIntOrNull() ?: 0
}
