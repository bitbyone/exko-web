package io.exko.html


val String.cssClass: CssClass
    get() = CssClass(this)

val String.id: Id
    get() = Id(this)

fun Id.cssSelector() = "#${this.value}"
fun CssClass.cssSelector() = ".${this.value}"

@JvmInline
value class Id(val value: String) {
    companion object {
        val random get() = randomId()
    }
}

@JvmInline
value class CssClass(val value: String)

private fun randomId(): String {
    val letters = ('a'..'z') + ('A'..'Z')
    val allAllowed = letters + ('0'..'9')

    // 1. Pick the first char only from letters
    val firstChar = letters.random()

    // 2. Pick the next 6 chars from letters + numbers
    val remainingChars = (1..6)
        .map { allAllowed.random() }
        .joinToString("")

    return firstChar + remainingChars
}
