package io.exko.html

import kotlinx.html.AttributeEnum
import kotlinx.html.attributes.Attribute
import kotlinx.html.attributes.AttributeEncoder

class EnumSetAttribute<T : AttributeEnum>(values: Map<String, T>) : Attribute<Set<T>>(EnumSetEncoder(values))

class EnumSetEncoder<T : AttributeEnum>(val valuesMap: Map<String, T>) : AttributeEncoder<Set<T>> {
    override fun encode(attributeName: String, value: Set<T>): String = value.joinToString(" ") { it.realValue }
    override fun decode(attributeName: String, value: String): Set<T> = enumSetDecode(value, valuesMap) ?: emptySet()
}

fun <T : AttributeEnum> enumSetDecode(value: String?, values: Map<String, T>): Set<T>? =
    value?.split("\\s+".toRegex())?.filterNot { it.isEmpty() }?.mapNotNull { values[it] }?.toSet() ?: emptySet()
