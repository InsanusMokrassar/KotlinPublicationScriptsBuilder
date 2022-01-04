package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.kmppscriptbuilder.core.models.*
import dev.inmo.kmppscriptbuilder.web.utils.ukActive
import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement

class MavenProjectInfoView : View {
    private val nameElement = document.getElementById("projectNameInput") as HTMLInputElement
    private val descriptionElement = document.getElementById("projectDescriptionInput") as HTMLInputElement
    private val urlElement = document.getElementById("projectUrlInput") as HTMLInputElement
    private val vcsUrlElement = document.getElementById("projectVCSUrlInput") as HTMLInputElement
    private val disableGpgSigningElement = document.getElementById("disableGpgSigning") as HTMLElement
    private val optionalGpgSigningElement = document.getElementById("optionalGpgSigning") as HTMLElement
    private val enableGpgSigningElement = document.getElementById("enableGpgSigning") as HTMLElement
    private val includeMavenCentralElement = document.getElementById("includeMavenCentralTargetRepoToggle") as HTMLInputElement
    private val developersView = DevelopersView(document.getElementById("developersListDiv") as HTMLElement)
    private val repositoriesView = RepositoriesView(document.getElementById("repositoriesListDiv") as HTMLElement)

    private var gpgSignMode: GpgSigning = GpgSigning.Disabled
        set(value) {
            field = value
            when (value) {
                GpgSigning.Enabled -> {
                    enableGpgSigningElement.ukActive = true
                    disableGpgSigningElement.ukActive = false
                    optionalGpgSigningElement.ukActive = false
                }
                GpgSigning.Optional -> {
                    enableGpgSigningElement.ukActive = false
                    disableGpgSigningElement.ukActive = false
                    optionalGpgSigningElement.ukActive = true
                }
                GpgSigning.Disabled -> {
                    enableGpgSigningElement.ukActive = false
                    disableGpgSigningElement.ukActive = true
                    optionalGpgSigningElement.ukActive = false
                }
            }
        }

    var mavenConfig: MavenConfig
        get() = MavenConfig(
            nameElement.value.ifBlank { defaultProjectName },
            descriptionElement.value.ifBlank { defaultProjectDescription },
            urlElement.value,
            vcsUrlElement.value,
            developersView.developers,
            repositoriesView.repositories + if (includeMavenCentralElement.checked) {
                listOf(SonatypeRepository)
            } else {
                emptyList()
            },
            when {
                optionalGpgSigningElement.ukActive -> GpgSigning.Optional
                enableGpgSigningElement.ukActive -> GpgSigning.Enabled
                else -> GpgSigning.Disabled
            }
        )
        set(value) {
            nameElement.value = value.name
            descriptionElement.value = value.description
            urlElement.value = value.url
            vcsUrlElement.value = value.vcsUrl
            gpgSignMode = if (value.includeGpgSigning) {
                GpgSigning.Enabled
            } else {
                value.gpgSigning
            }
            developersView.developers = value.developers
            val reposWithoutSonatype = value.repositories.filter { it != SonatypeRepository }
            includeMavenCentralElement.checked = value.repositories.size != reposWithoutSonatype.size
            repositoriesView.repositories = reposWithoutSonatype
        }

    init {
        enableGpgSigningElement.onclick = { gpgSignMode = GpgSigning.Enabled; Unit }
        disableGpgSigningElement.onclick = { gpgSignMode = GpgSigning.Disabled; Unit }
        optionalGpgSigningElement.onclick = { gpgSignMode = GpgSigning.Optional; Unit }
    }
}
