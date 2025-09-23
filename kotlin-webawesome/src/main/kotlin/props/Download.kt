package io.exko.webawesome.props

import kotlinx.html.Tag

interface DownloadAware : Tag {

    companion object {
        const val ATTR_NAME = "download"
    }

    var download: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}
