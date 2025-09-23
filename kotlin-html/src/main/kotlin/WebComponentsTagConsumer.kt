package io.exko.html

import kotlinx.html.TagConsumer


class WebComponentsTagConsumer<T>(
    private val delegate: TagConsumer<T>
) : TagConsumer<T> by delegate

interface WebComponent
