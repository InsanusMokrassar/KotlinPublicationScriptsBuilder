package dev.inmo.kmppscriptbuilder.web.views

import kotlinx.dom.appendElement
import org.w3c.dom.*
import kotlin.random.Random

fun HTMLElement.createTextField(
    label: String,
    placeholder: String
): HTMLInputElement {
    val uuid = "r" + Random.nextLong()
    return appendElement("div") {
        classList.add("uk-margin", "uk-width-1-1")
        appendElement("label") {
            classList.add("uk-form-label")
            setAttribute("for", uuid)
            innerText = label
        }
    }.appendElement("input") {
        id = uuid
        classList.add("uk-input", "uk-width-expand")
        setAttribute("type", "text")
        setAttribute("placeholder", placeholder)
    } as HTMLInputElement
}

fun HTMLElement.createButton(text: String): HTMLButtonElement = appendElement("button") {
    classList.add("uk-button", "uk-button-primary")
    innerHTML = text
} as HTMLButtonElement
