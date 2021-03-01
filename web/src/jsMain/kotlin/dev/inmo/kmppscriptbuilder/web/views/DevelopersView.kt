package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.kmppscriptbuilder.core.models.Developer
import org.w3c.dom.*

class DevelopersView(rootElement: HTMLElement) : ListView<Developer>(rootElement, "Add developer", "Remove developer") {
    private val HTMLElement.usernameElement: HTMLInputElement
        get() = children[0] as HTMLInputElement
    private val HTMLElement.nameElement: HTMLInputElement
        get() = children[1] as HTMLInputElement
    private val HTMLElement.emailElement: HTMLInputElement
        get() = children[2] as HTMLInputElement

    var developers: List<Developer>
        get() = elements.values.map {
            Developer(it.usernameElement.value, it.nameElement.value, it.emailElement.value)
        }
        set(value) {
            data = value
        }

    override fun createPlainObject(): Developer = Developer("", "", "")

    override fun HTMLElement.placeElement(value: Developer) {
        createTextField("Developer ID", "Developer username").value = value.id
        createTextField("Developer name", "").value = value.name
        createTextField("Developer E-Mail", "").value = value.eMail
    }

    override fun HTMLElement.updateElement(from: Developer, to: Developer) {
        usernameElement.value = to.id
        nameElement.value = to.name
        emailElement.value = to.eMail
    }
}