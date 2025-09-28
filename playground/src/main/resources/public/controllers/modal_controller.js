import {Controller} from "@hotwired/stimulus"

export default class extends Controller {
    static targets = ["dialog"]

    connect() {
        console.log("Hello, Stimulus!", this.element)
    }

    open() {
        this.dialogTarget.open = true
    }
}
