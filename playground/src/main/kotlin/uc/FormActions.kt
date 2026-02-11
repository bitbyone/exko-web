package io.exko.sandbox.uikit.uc

import io.exko.html.*
import io.exko.htmx.dsl.hx
import io.exko.sandbox.uikit.layout.MainLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.card.Card
import io.exko.webawesome.components.dialog.Dialog
import io.exko.webawesome.components.input.Input
import io.exko.webawesome.components.option.Option
import io.exko.webawesome.components.select.Select
import io.exko.webawesome.components.textarea.Textarea
import io.exko.webawesome.props.InputType
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/uc/forms")
@Controller
class FormActionsController {

    @GetMapping
    fun renderPage() = view(page = FormsPage(), layout = ::MainLayout)

    @PostMapping("/form-1")
    fun handleForm1(@ModelAttribute form: FavAnimalForm): Render {
        return view(FormConfirm(form), ::MainLayout)
    }
}

@UI
fun FormsPage() = fragment {
    h1 { +"Form Actions" }

    Card("example-form-card") {
        slotHeader { span("wa-heading-s") { +"Simple form submit and server confirmation" } }
        FavAnimalForm("form")
    }

    br

    div {
        attributes["data-controller"] = "modal"

        Button {
            attributes["data-action"] = "click->modal#open"
            +"Open Modal"
        }

        Dialog(label = "Form in modal", lightDismiss = true) {
            attributes["data-modal-target"] = "dialog"
            FavAnimalForm("form-modal")
        }
    }
}

@UI
fun Component.FavAnimalForm(formId: String) {
    form(action = "forms/form-1", method = FormMethod.post) {
        id = formId
        hx {
            +("target" to "closest form")
            +("swap" to "outerHTML")
            +("push-url" to "false")
        }
        Input(name = FavAnimalForm::name.name, label = "Name", type = InputType.text, required = true, autofocus = true)
        br
        Select(name = FavAnimalForm::favAnimal.name, label = "Favorite Animal", withClear = true, required = true) {
            FavAnimal.values().sortedBy { it.sort }.forEach {
                Option(value = it.name) { +it.label }
            }
        }
        br
        Textarea(name = FavAnimalForm::comment.name, label = "Comment")
        br
        Button(type = ButtonType.submit) { +"Save" }
    }
}

@UI
fun FormConfirm(form: FavAnimalForm) = fragment {
    span { +"Saved" }
    p { +"${form.name} -> ${form.favAnimal.name} -> ${form.comment}" }
}

enum class FavAnimal(
    val label: String,
    val sort: Int = 0,
) {
    DOG("Dogs", 0),
    CAT("Cats", 1),
    FISH("Fishes", 2),
    OTHER("Other", 3),
}

data class FavAnimalForm(
    val name: String,
    val favAnimal: FavAnimal,
    val comment: String? = null,
)
