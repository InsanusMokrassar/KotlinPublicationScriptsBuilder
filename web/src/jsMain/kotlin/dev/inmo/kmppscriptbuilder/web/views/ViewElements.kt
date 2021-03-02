package dev.inmo.kmppscriptbuilder.web.views

import kotlinx.dom.appendElement
import org.w3c.dom.*

fun HTMLElement.createTextField(
    label: String,
    placeholder: String
): HTMLInputElement {
    return appendElement("div") {
        classList.add("uk-margin", "uk-width-1-1")
    }.appendElement("label") {
        classList.add("uk-form-label")
        innerHTML = label
    }.run {
        val input = appendElement("input") {
            classList.add("uk-input", "uk-width-expand")
            setAttribute("type", "text")
            setAttribute("placeholder", placeholder)
        } as HTMLInputElement
        input
    }
}

fun HTMLElement.createPrimaryButton(text: String): HTMLButtonElement = (appendElement("button") {
    classList.add("uk-button", "uk-button-primary")
} as HTMLButtonElement).apply {
    innerText = text
}

fun HTMLElement.createCommonButton(text: String): HTMLButtonElement = (appendElement("button") {
    classList.add("uk-button", "uk-button-default")
} as HTMLButtonElement).apply {
    innerText = text
}
