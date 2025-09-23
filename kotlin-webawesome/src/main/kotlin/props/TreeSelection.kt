package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

/**
 * Tree selection supports only "single", "multiple", and "leaf" according to wa-tree.d.ts
 */
enum class TreeSelection(override val realValue: String) : AttributeEnum {
    single("single"),
    multiple("multiple"),
    leaf("leaf");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val treeSelectionAttr = EnumAttribute(TreeSelection.valuesMap)

interface TreeSelectionAware : Tag {
    companion object {
        const val ATTR_NAME = "selection"
    }

    var selection: TreeSelection
        get() = treeSelectionAttr[this, ATTR_NAME]
        set(value) {
            treeSelectionAttr[this, ATTR_NAME] = value
        }
}
