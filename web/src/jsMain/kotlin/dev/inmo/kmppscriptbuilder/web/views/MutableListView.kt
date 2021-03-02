package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.kmppscriptbuilder.web.utils.keepScrolling
import org.w3c.dom.HTMLElement

abstract class MutableListView<T>(
    rootElement: HTMLElement,
    addButtonText: String = "Add",
    private val removeButtonText: String = "Remove"
) : ListView<T>(rootElement) {
    init {
        rootElement.createPrimaryButton(addButtonText).apply {
            onclick = {
                keepScrolling {
                    val newObject = createPlainObject()
                    data += newObject
                }
                false
            }
        }
    }

    protected abstract fun createPlainObject(): T
    protected open fun HTMLElement.addContentBeforeRemoveButton(value: T) {}
    protected open fun HTMLElement.addContentAfterRemoveButton(value: T) {}
    final override fun HTMLElement.placeElement(value: T) {
        addContentBeforeRemoveButton(value)
        addRemoveButton()
        addContentAfterRemoveButton(value)
    }

    private fun HTMLElement.addRemoveButton() {
        val button = createPrimaryButton(removeButtonText)
        button.onclick = {
            elements.indexOf(button.parentElement).takeIf { it > -1 } ?.also {
                data -= data[it]
            } ?: rootElement.removeChild(this@addRemoveButton)
            false
        }
    }
}