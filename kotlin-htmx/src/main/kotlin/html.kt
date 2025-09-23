package io.exko.htmx

import kotlinx.html.FlowContent
import java.nio.file.Paths
import kotlin.properties.ReadOnlyProperty

/* This file is obsolete now */

data class HTMX(
    val isHxRequest: Boolean,
    var pageTitle: String? = null,
)

interface HxSpecs {
    val id: String
    val target: String
    val swap: String
}

// -------------------- Experimental ideas -------------------------

abstract class RootRoute(root: String = "/") : Route(root)

abstract class Route(
    root: String? = null,
    private val parent: Route? = null
) {

    private val _root: String = root ?: this::class.simpleName?.lowercase() ?: "/"

    fun path(path: String): String {
        val p = when {
            parent == null -> "/$_root/$path/"
            else -> "/${parent.index}/$_root/$path/"
        }
        return Paths.get(p).normalize().toString()
    }

    val index: String = path("")

    fun route(path: String? = null): ReadOnlyProperty<Route, String> =
        ReadOnlyProperty { _, prop -> if (path == null) path(prop.name) else path(path) }

    fun router(path: String? = null, block: () -> Unit): ReadOnlyProperty<Route, String> =
        ReadOnlyProperty { _, prop -> if (path == null) path(prop.name) else path(path) }
}

fun FlowContent.use(hxSpecs: HxSpecs) {
    attributes["hx-target"] = hxSpecs.target
    attributes["hx-swap"] = hxSpecs.swap
}

