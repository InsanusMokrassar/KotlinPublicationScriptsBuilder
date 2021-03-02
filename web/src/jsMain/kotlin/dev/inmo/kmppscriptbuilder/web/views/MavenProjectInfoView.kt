package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.kmppscriptbuilder.core.models.MavenConfig
import dev.inmo.kmppscriptbuilder.core.models.SonatypeRepository
import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement

class MavenProjectInfoView : View {
    private val nameElement = document.getElementById("projectNameInput") as HTMLInputElement
    private val descriptionElement = document.getElementById("projectDescriptionInput") as HTMLInputElement
    private val urlElement = document.getElementById("projectUrlInput") as HTMLInputElement
    private val vcsUrlElement = document.getElementById("projectVCSUrlInput") as HTMLInputElement
    private val includeGpgElement = document.getElementById("includeGpgSignToggle") as HTMLInputElement
    private val includeMavenCentralElement = document.getElementById("includeMavenCentralTargetRepoToggle") as HTMLInputElement
    private val developersView = DevelopersView(document.getElementById("developersListDiv") as HTMLElement)
    private val repositoriesView = RepositoriesView(document.getElementById("repositoriesListDiv") as HTMLElement)

    var mavenConfig: MavenConfig
        get() = MavenConfig(
            nameElement.value,
            descriptionElement.value,
            urlElement.value,
            vcsUrlElement.value,
            includeGpgElement.checked,
            developersView.developers,
            repositoriesView.repositories + if (includeMavenCentralElement.checked) {
                listOf(SonatypeRepository)
            } else {
                emptyList()
            }
        )
        set(value) {
            nameElement.value = value.name
            descriptionElement.value = value.description
            urlElement.value = value.url
            vcsUrlElement.value = value.vcsUrl
            includeGpgElement.checked = value.includeGpgSigning
            developersView.developers = value.developers
            val reposWithoutSonatype = value.repositories.filter { it != SonatypeRepository }
            includeMavenCentralElement.checked = value.repositories.size != reposWithoutSonatype.size
            repositoriesView.repositories = value.repositories
        }
}