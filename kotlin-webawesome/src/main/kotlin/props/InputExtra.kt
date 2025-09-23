package io.exko.webawesome.props

import kotlinx.html.AttributeEnum
import kotlinx.html.Tag
import kotlinx.html.attributes.EnumAttribute

interface PatternAware : Tag {
    companion object {
        const val ATTR_NAME = "pattern"
    }

    var pattern: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}

interface MinLengthAware : Tag {
    companion object {
        const val ATTR_NAME = "minlength"
    }

    var minlength: Int
        get() = intAttr[this, ATTR_NAME]
        set(value) {
            intAttr[this, ATTR_NAME] = value
        }
}

interface MaxLengthAware : Tag {
    companion object {
        const val ATTR_NAME = "maxlength"
    }

    var maxlength: Int
        get() = intAttr[this, ATTR_NAME]
        set(value) {
            intAttr[this, ATTR_NAME] = value
        }
}

interface MinAware : Tag {
    companion object {
        const val ATTR_NAME = "min"
    }

    var min: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}

interface MaxAware : Tag {
    companion object {
        const val ATTR_NAME = "max"
    }

    var max: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}

interface StepAware : Tag {
    companion object {
        const val ATTR_NAME = "step"
    }

    var step: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}

enum class AutoCapitalize(override val realValue: String) : AttributeEnum {
    off("off"), none("none"), on("on"), sentences("sentences"), words("words"), characters("characters");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val autoCapitalizeAttr = EnumAttribute(AutoCapitalize.valuesMap)

interface AutocapitalizeAware : Tag {
    companion object {
        const val ATTR_NAME = "autocapitalize"
    }

    var autocapitalize: AutoCapitalize
        get() = autoCapitalizeAttr[this, ATTR_NAME]
        set(value) {
            autoCapitalizeAttr[this, ATTR_NAME] = value
        }
}

enum class AutoCorrect(override val realValue: String) : AttributeEnum {
    off("off"), on("on");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val autoCorrectAttr = EnumAttribute(AutoCorrect.valuesMap)

interface AutocorrectAware : Tag {
    companion object {
        const val ATTR_NAME = "autocorrect"
    }

    var autocorrect: AutoCorrect
        get() = autoCorrectAttr[this, ATTR_NAME]
        set(value) {
            autoCorrectAttr[this, ATTR_NAME] = value
        }
}

interface AutocompleteAware : Tag {
    companion object {
        const val ATTR_NAME = "autocomplete"
    }

    var autocomplete: String
        get() = stringAttr[this, ATTR_NAME]
        set(value) {
            stringAttr[this, ATTR_NAME] = value
        }
}

interface AutofocusAware : Tag {
    companion object {
        const val ATTR_NAME = "autofocus"
    }

    var autofocus: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}

enum class EnterKeyHint(override val realValue: String) : AttributeEnum {
    enter("enter"), done("done"), go("go"), next("next"), previous("previous"), search("search"), send("send");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val enterKeyHintAttr = EnumAttribute(EnterKeyHint.valuesMap)

interface EnterKeyHintAware : Tag {
    companion object {
        const val ATTR_NAME = "enterkeyhint"
    }

    var enterkeyhint: EnterKeyHint
        get() = enterKeyHintAttr[this, ATTR_NAME]
        set(value) {
            enterKeyHintAttr[this, ATTR_NAME] = value
        }
}

interface SpellcheckAware : Tag {
    companion object {
        const val ATTR_NAME = "spellcheck"
    }

    var spellcheck: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}

enum class InputMode(override val realValue: String) : AttributeEnum {
    none("none"), text("text"), decimal("decimal"), numeric("numeric"), tel("tel"), search("search"), email("email"), url("url");

    companion object {
        val valuesMap = entries.associateBy { it.realValue }
    }
}

internal val inputModeAttr = EnumAttribute(InputMode.valuesMap)

interface InputModeAware : Tag {
    companion object {
        const val ATTR_NAME = "inputmode"
    }

    var inputmode: InputMode
        get() = inputModeAttr[this, ATTR_NAME]
        set(value) {
            inputModeAttr[this, ATTR_NAME] = value
        }
}

interface WithLabelAware : Tag {
    companion object {
        const val ATTR_NAME = "with-label"
    }

    var withLabel: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}

interface WithHintAware : Tag {
    companion object {
        const val ATTR_NAME = "with-hint"
    }

    var withHint: Boolean
        get() = tickerAttr[this, ATTR_NAME]
        set(value) {
            tickerAttr[this, ATTR_NAME] = value
        }
}
