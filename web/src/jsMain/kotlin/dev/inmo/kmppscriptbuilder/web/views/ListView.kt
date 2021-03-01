package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.micro_utils.common.calculateDiff
import kotlinx.browser.document
import kotlinx.dom.appendElement
import org.w3c.dom.HTMLElement

abstract class ListView<T>(
    private val rootElement: HTMLElement,
    addButtonText: String = "Add",
    private val removeButtonText: String = "Remove"
) : View {
    protected val elements = mutableMapOf<T, HTMLElement>()
    protected var data: List<T> = emptyList()
        set(value) {
            val old = field
            field = value
            val diff = old.calculateDiff(value)
            diff.removed.forEach {
                rootElement.removeChild(elements[it.value] ?: return@forEach)
            }
            diff.added.forEach {
                val element = instantiateElement()
                element.placeElement(it.value)
                elements[it.value] = element
                element.addRemoveButton(it.value)
            }
            diff.replaced.forEach { (old, new) ->
                val element = elements[old.value] ?.also { it.updateElement(old.value, new.value) }
                if (element == null) {
                    val newElement = instantiateElement()
                    newElement.placeElement(new.value)
                    elements[new.value] = newElement
                }
            }
        }

    init {
        rootElement.createButton(addButtonText).apply {
            onclick = {
                data += createPlainObject()
                Unit
            }
        }
    }

    protected abstract fun createPlainObject(): T
    protected abstract fun HTMLElement.placeElement(value: T)
    protected abstract fun HTMLElement.updateElement(from: T, to: T)

    private fun HTMLElement.addRemoveButton(value: T) {
        createButton(removeButtonText).onclick = {
            data = data.filter {
                it != value
            }
            Unit
        }
    }
    private fun instantiateElement() = rootElement.appendElement("div") {
        classList.add("uk-padding-small")
    } as HTMLElement
}