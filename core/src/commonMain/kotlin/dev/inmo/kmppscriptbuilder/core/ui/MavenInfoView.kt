package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.*
import dev.inmo.kmppscriptbuilder.core.models.GpgSigning
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

expect object MavenInfoDrawer : Drawer<MavenInfoView>

class MavenInfoView : VerticalView("Project information") {
    internal var projectNameProperty by mutableStateOf("")
    internal var projectDescriptionProperty by mutableStateOf("")
    internal var projectUrlProperty by mutableStateOf("")
    internal var projectVcsUrlProperty by mutableStateOf("")
    internal var gpgSignProperty by mutableStateOf<GpgSigning>(GpgSigning.Disabled)
    internal var publishToMavenCentralProperty by mutableStateOf(false)
    internal val developersView = DevelopersView()
    internal val repositoriesView = RepositoriesView()

    var mavenConfig: MavenConfig
        get() = MavenConfig(
            projectNameProperty.ifBlank { defaultProjectName },
            projectDescriptionProperty.ifBlank { defaultProjectDescription },
            projectUrlProperty,
            projectVcsUrlProperty,
            developersView.developers,
            repositoriesView.repositories + if (publishToMavenCentralProperty) {
                listOf(SonatypeRepository)
            } else {
                emptyList()
            },
            gpgSignProperty
        )
        set(value) {
            projectNameProperty = value.name
            projectDescriptionProperty = value.description
            projectUrlProperty = value.url
            projectVcsUrlProperty = value.vcsUrl
            gpgSignProperty = if (value.includeGpgSigning) {
                GpgSigning.Enabled
            } else {
                value.gpgSigning
            }
            publishToMavenCentralProperty = value.repositories.any { it == SonatypeRepository }
            developersView.developers = value.developers
            repositoriesView.repositories = value.repositories.filter { it != SonatypeRepository }
//            developersView.developers = value.developers
        }

    override val content: @Composable () -> Unit = {
        with (MavenInfoDrawer) { draw() }
    }
}
