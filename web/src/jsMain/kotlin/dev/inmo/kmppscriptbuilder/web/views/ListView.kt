package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.micro_utils.common.calculateStrictDiff
import kotlinx.dom.appendElement
import org.w3c.dom.HTMLElement

abstract class ListView<T>(
    protected val rootElement: HTMLElement,
    useSimpleDiffStrategy: Boolean = false
) : View {
    protected val elements = mutableListOf<HTMLElement>()
    private val diffHandling: (old: List<T>, new: List<T>) -> Unit = if (useSimpleDiffStrategy) {
        { _, new ->
            elements.forEach { it.remove() }
            elements.clear()
            new.forEach {
                val element = instantiateElement()
                elements.add(element)
                element.placeElement(it)
            }
        }
    } else {
        {  old, new ->
            val diff = old.calculateStrictDiff(new)
            diff.removed.forEach {
                elements[it.index].remove()
                elements.removeAt(it.index)
                println(it.value)
            }
            diff.added.forEach {
                val element = instantiateElement()
                elements.add(element)
                element.placeElement(it.value)
            }
            diff.replaced.forEach { (old, new) ->
                val element = elements.getOrNull(old.index) ?.also { it.updateElement(old.value, new.value) }
                if (element == null) {
                    val newElement = instantiateElement()
                    newElement.placeElement(new.value)
                    elements[new.index] = newElement
                }
            }
        }
    }
    protected var data: List<T> = emptyList()
        set(value) {
            val old = field
            field = value
            diffHandling(old, value)
        }

    protected abstract fun HTMLElement.placeElement(value: T)
    protected abstract fun HTMLElement.updateElement(from: T, to: T)

    private fun instantiateElement() = rootElement.appendElement("div") {
        classList.add("uk-padding-small")
    } as HTMLElement
}