package io.exko.webawesome.props

import kotlinx.html.attributes.Attribute
import kotlinx.html.attributes.AttributeEncoder
import kotlinx.html.attributes.StringAttribute
import kotlinx.html.attributes.TickerAttribute

internal val tickerAttr: TickerAttribute = TickerAttribute()
internal val stringAttr: StringAttribute = StringAttribute()
internal val intAttr: IntAttribute = IntAttribute()
internal val doubleAttr: DoubleAttribute = DoubleAttribute()

internal class IntAttribute : Attribute<Int>(IntEncoder)
internal class DoubleAttribute : Attribute<Double>(DoubleEncoder)

internal object IntEncoder : AttributeEncoder<Int> {
    override fun encode(attributeName: String, value: Int): String = value.toString()
    override fun decode(attributeName: String, value: String): Int = value.toIntOrNull() ?: 0
}

internal object DoubleEncoder : AttributeEncoder<Double> {
    override fun encode(attributeName: String, value: Double): String = value.toString()
    override fun decode(attributeName: String, value: String): Double = value.toDoubleOrNull() ?: 0.0
}
