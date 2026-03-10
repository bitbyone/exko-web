package io.exko.htmx.dsl

// HTTP method request attributes: hx-get, hx-post, hx-put, hx-patch, hx-delete

private class HxMethod(private val method: String, private val url: String) : HxAttribute {
    override val name: String = "hx-$method"
    override fun content(): String = url
}

fun HxAttributes.get(url: String) = add(HxMethod("get", url))
fun HxAttributes.post(url: String) = add(HxMethod("post", url))
fun HxAttributes.put(url: String) = add(HxMethod("put", url))
fun HxAttributes.patch(url: String) = add(HxMethod("patch", url))
fun HxAttributes.delete(url: String) = add(HxMethod("delete", url))
