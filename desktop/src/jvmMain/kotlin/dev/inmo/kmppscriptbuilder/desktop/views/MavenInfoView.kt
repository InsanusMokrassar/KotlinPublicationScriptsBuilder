package dev.inmo.kmppscriptbuilder.desktop.views

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.*
import dev.inmo.kmppscriptbuilder.core.models.*
import dev.inmo.kmppscriptbuilder.desktop.utils.*

class MavenInfoView : VerticalView("Project information") {
    private var projectNameProperty by mutableStateOf("")
    private var projectDescriptionProperty by mutableStateOf("")
    private var projectUrlProperty by mutableStateOf("")
    private var projectVcsUrlProperty by mutableStateOf("")
    private var includeGpgSignProperty by mutableStateOf(true)
    private var publishToMavenCentralProperty by mutableStateOf(false)
    private val developersView = DevelopersView()
    private val repositoriesView = RepositoriesView()

    var mavenConfig: MavenConfig
        get() = MavenConfig(
            projectNameProperty.ifBlank { defaultProjectName },
            projectDescriptionProperty.ifBlank { defaultProjectDescription },
            projectUrlProperty,
            projectVcsUrlProperty,
            includeGpgSignProperty,
            developersView.developers,
            repositoriesView.repositories + if (publishToMavenCentralProperty) {
                listOf(SonatypeRepository)
            } else {
                emptyList()
            }
        )
        set(value) {
            projectNameProperty = value.name
            projectDescriptionProperty = value.description
            projectUrlProperty = value.url
            projectVcsUrlProperty = value.vcsUrl
            includeGpgSignProperty = value.includeGpgSigning
            publishToMavenCentralProperty = value.repositories.any { it == SonatypeRepository }
            developersView.developers = value.developers
            repositoriesView.repositories = value.repositories.filter { it != SonatypeRepository }
//            developersView.developers = value.developers
        }

    override val content: @Composable ColumnScope.() -> Unit = {
        CommonTextField(
            projectNameProperty,
            "Public project name"
        ) { projectNameProperty = it }
        CommonTextField(
            projectDescriptionProperty,
            "Public project description"
        ) { projectDescriptionProperty = it }
        CommonTextField(
            projectUrlProperty,
            "Public project URL"
        ) { projectUrlProperty = it }
        CommonTextField(
            projectVcsUrlProperty,
            "Public project VCS URL (with .git)"
        ) { projectVcsUrlProperty = it }

        SwitchWithLabel(
            "Include GPG Signing",
            includeGpgSignProperty,
            placeSwitchAtTheStart = true
        ) { includeGpgSignProperty = it }

        SwitchWithLabel(
            "Include publication to MavenCentral",
            publishToMavenCentralProperty,
            placeSwitchAtTheStart = true
        ) { publishToMavenCentralProperty = it }
        developersView.init()
        repositoriesView.init()
    }
}