package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.*
import dev.inmo.kmppscriptbuilder.core.models.GpgSigning
import dev.inmo.kmppscriptbuilder.core.models.MavenConfig
import dev.inmo.kmppscriptbuilder.core.models.SonatypeRepository
import dev.inmo.kmppscriptbuilder.core.models.defaultProjectDescription
import dev.inmo.kmppscriptbuilder.core.models.defaultProjectName
import dev.inmo.kmppscriptbuilder.core.ui.utils.ButtonsPanel
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.DefaultSmallVerticalMargin
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
        CommonText("Public project name")
        CommonTextField(
            projectNameProperty,
            "\${project.name}",
        ) { projectNameProperty = it }
        DefaultSmallVerticalMargin()
        CommonText("Public project description")
        CommonTextField(
            projectDescriptionProperty,
            "\${project.name}",
        ) { projectDescriptionProperty = it }
        DefaultSmallVerticalMargin()
        CommonText("Public project URL")
        CommonTextField(
            projectUrlProperty,
            "Type url to github or other source with readme",
        ) { projectUrlProperty = it }
        DefaultSmallVerticalMargin()
        CommonText("Public project VCS URL (with .git)")
        CommonTextField(
            projectVcsUrlProperty,
            "Type url to github .git file"
        ) { projectVcsUrlProperty = it }

        ButtonsPanel(
            "Gpg signing",
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
