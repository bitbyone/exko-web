package io.exko.htmx.dsl

// hx-encoding — set the encoding type for the request (needed for file uploads)
class HxEncoding : HxAttribute {
    override val name: String = "hx-encoding"
    private var value: String = "multipart/form-data"

    override fun content(): String = value

    fun multipartFormData(): HxEncoding { value = "multipart/form-data"; return this }
}

// Convenience — the only practical use case is multipart/form-data for file uploads
fun HxAttributes.encodingMultipart() {
    add(HxEncoding().multipartFormData())
}
