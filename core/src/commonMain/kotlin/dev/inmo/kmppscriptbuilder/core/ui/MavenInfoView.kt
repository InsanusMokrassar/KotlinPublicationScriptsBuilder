package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.*
import dev.inmo.kmppscriptbuilder.core.models.GpgSigning
import dev.inmo.kmppscriptbuilder.core.models.MavenConfig
import dev.inmo.kmppscriptbuilder.core.models.SonatypeRepository
import dev.inmo.kmppscriptbuilder.core.models.defaultProjectDescription
import dev.inmo.kmppscriptbuilder.core.models.defaultProjectName
import dev.inmo.kmppscriptbuilder.core.ui.utils.ButtonsPanel
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer
import dev.inmo.kmppscriptbuilder.core.ui.utils.SwitchWithLabel

expect class GpgSigningOptionDrawer : Drawer<GpgSigning>
expect fun GpgSigningOptionDrawerWithView(view: MavenInfoView): GpgSigningOptionDrawer

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
        }

    private val gpgSigningDrawer = GpgSigningOptionDrawerWithView(this)

    override val content: @Composable () -> Unit = {
        CommonTextField(
            projectNameProperty,
            "Public project name",
        ) { projectNameProperty = it }
        CommonTextField(
            projectDescriptionProperty,
            "Public project description",
        ) { projectDescriptionProperty = it }
        CommonTextField(
            projectUrlProperty,
            "Public project URL",
        ) { projectUrlProperty = it }
        CommonTextField(
            projectVcsUrlProperty,
            "Public project VCS URL (with .git)",
        ) { projectVcsUrlProperty = it }

        ButtonsPanel(
            GpgSigning.Disabled,
            GpgSigning.Optional,
            GpgSigning.Enabled
        ) {
            with(gpgSigningDrawer) {
                with (it) {
                    draw()
                }
            }
        }

        SwitchWithLabel(
            "Include publication to MavenCentral",
            publishToMavenCentralProperty,
            placeSwitchAtTheStart = true
        ) { publishToMavenCentralProperty = it }
        developersView.build()
        repositoriesView.build()
    }
}
