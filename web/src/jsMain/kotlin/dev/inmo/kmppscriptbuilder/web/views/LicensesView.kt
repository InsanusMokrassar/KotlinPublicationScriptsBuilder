package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.kmppscriptbuilder.core.models.License
import dev.inmo.kmppscriptbuilder.core.models.getLicenses
import dev.inmo.micro_utils.coroutines.safeActor
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import io.ktor.client.HttpClient
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.dom.appendElement
import org.w3c.dom.*

class LicensesView(
    rootElement: HTMLElement,
    client: HttpClient = HttpClient(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : MutableListView<License>(rootElement, "Add empty license", "Remove license") {
    private val HTMLElement.idElement: HTMLInputElement
        get() = getElementsByTagName("input")[0] as HTMLInputElement
    private val HTMLElement.titleElement: HTMLInputElement
        get() = getElementsByTagName("input")[1] as HTMLInputElement
    private val HTMLElement.urlElement: HTMLInputElement
        get() = getElementsByTagName("input")[2] as HTMLInputElement

    private class LicenseOfferList(
        rootElement: HTMLElement,
        private val licensesView: LicensesView,
        client: HttpClient,
        scope: CoroutineScope
    ) : ListView<License>(rootElement, useSimpleDiffStrategy = true) {
        private var licensesTemplates: List<License> = emptyList()

        init {
            scope.launch {
                licensesTemplates = client.getLicenses().values.toList()
                changeActor.send(Unit) // update list of searches
            }
        }

        private val changeActor: SendChannel<Unit> = scope.run {
            val onChangeActor = Channel<Unit>(Channel.CONFLATED)
            onChangeActor.consumeAsFlow().subscribeSafelyWithoutExceptions(scope) {
                val lowercased = searchString
                data = if (lowercased.isEmpty()) {
                    emptyList()
                } else {
                    licensesTemplates.filter {
                        val lowercasedTitle = it.title.toLowerCase()
                        lowercased.all { it in lowercasedTitle }
                    }
                }
            }
            onChangeActor
        }
        private val searchElement = rootElement.createTextField("Quick add", "Type some license name part to find it").apply {
            oninput = {
                changeActor.offer(Unit)
                false
            }
        }
        private var searchString: String
            get() = searchElement.value.toLowerCase()
            set(value) {
                searchElement.value = value
            }

        override fun HTMLElement.placeElement(value: License) {
            createCommonButton(value.title).onclick = {
                searchString = ""
                licensesView.licenses += value
                changeActor.offer(Unit)
                false
            }
        }

        override fun HTMLElement.updateElement(from: License, to: License) {
            getElementsByTagName("button")[0] ?.remove()
            placeElement(to)
        }
    }

    private val licensesOffersList = LicenseOfferList(
        rootElement.appendElement("div") { classList.add("uk-padding-small") } as HTMLElement,
        this,
        client,
        scope
    )

    var licenses: List<License>
        get() = elements.map {
            License(it.idElement.value, it.titleElement.value, it.urlElement.value)
        }
        set(value) {
            data = value
        }

    override fun createPlainObject(): License = License("", "", "")

    override fun HTMLElement.addContentBeforeRemoveButton(value: License) {
        createTextField("License Id", "Short name like \"Apache-2.0\"").value = value.id
        createTextField("License Title", "Official title of license (like \"Apache Software License 2.0\")").value = value.title
        createTextField("License URL", "Link to your LICENSE file OR official license file (like \"https://opensource.org/licenses/Apache-2.0\")").value = value.url ?: ""
    }

    override fun HTMLElement.updateElement(from: License, to: License) {
        idElement.value = to.id
        titleElement.value = to.title
        urlElement.value = to.url ?: ""
    }
}