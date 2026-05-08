package io.exko.styled

import java.util.ServiceLoader

fun interface StyledRegistrar {

    fun register(bundler: StyledBundler)
}

private var refreshCallback: () -> Unit = {}

fun registerStyled(callback: (() -> Unit)? = null) {
    callback?.let { refreshCallback = it }
    StyledBundler.clear()
    ServiceLoader.load(StyledRegistrar::class.java).forEach {
        it.register(StyledBundler)
    }
    refreshCallback()
}
