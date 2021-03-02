package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.kmppscriptbuilder.core.models.MavenPublishingRepository
import org.w3c.dom.*

class RepositoriesView(rootElement: HTMLElement) : MutableListView<MavenPublishingRepository>(rootElement, "Add repository", "Remove repository") {
    private val HTMLElement.nameElement: HTMLInputElement
        get() = getElementsByTagName("input")[0] as HTMLInputElement
    private val HTMLElement.urlElement: HTMLInputElement
        get() = getElementsByTagName("input")[1] as HTMLInputElement

    var repositories: List<MavenPublishingRepository>
        get() = elements.map {
            MavenPublishingRepository(it.nameElement.value, it.urlElement.value)
        }
        set(value) {
            data = value
        }

    override fun createPlainObject(): MavenPublishingRepository = MavenPublishingRepository("", "")

    override fun HTMLElement.addContentBeforeRemoveButton(value: MavenPublishingRepository) {
        createTextField("Repository name", "This name will be used to identify repository in grade").value = value.name
        createTextField("Repository URL", "For example: https://repo.maven.apache.org/maven2/").value = value.name
    }

    override fun HTMLElement.updateElement(from: MavenPublishingRepository, to: MavenPublishingRepository) {
        nameElement.value = to.name
        urlElement.value = to.url
    }
}