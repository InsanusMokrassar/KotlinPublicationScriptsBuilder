package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.*
import dev.inmo.kmppscriptbuilder.core.models.CentralSonatypeRepository
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
    internal var publishToCentralSonatypeProperty by mutableStateOf(false)
    internal var includeCentralSonatypeUploadingScriptProperty by mutableStateOf(false)
    internal val developersView = DevelopersView()
    internal val repositoriesView = RepositoriesView()

    var mavenConfig: MavenConfig
        get() = MavenConfig(
            name = projectNameProperty.ifBlank { defaultProjectName },
            description = projectDescriptionProperty.ifBlank { defaultProjectDescription },
            url = projectUrlProperty,
            vcsUrl = projectVcsUrlProperty,
            developers = developersView.developers,
            repositories = repositoriesView.repositories + if (publishToMavenCentralProperty) {
                listOf(
                    if (publishToCentralSonatypeProperty) {
                        CentralSonatypeRepository
                    } else {
                        SonatypeRepository
                    }
                )
            } else {
                emptyList()
            },
            gpgSigning = gpgSignProperty,
            includeCentralSonatypeUploadingScript = includeCentralSonatypeUploadingScriptProperty
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
            publishToMavenCentralProperty = value.repositories.any { it == SonatypeRepository || it == CentralSonatypeRepository }
            developersView.developers = value.developers
            repositoriesView.repositories = value.repositories.filter { it != SonatypeRepository && it != CentralSonatypeRepository }
            publishToCentralSonatypeProperty = value.repositories.any { it == CentralSonatypeRepository }
            includeCentralSonatypeUploadingScriptProperty = value.includeCentralSonatypeUploadingScript
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
        if (publishToMavenCentralProperty) {
            SwitchWithLabel(
                "Use Central Sonatype instead of OSSRH (OSSRH has been deprecated)",
                publishToCentralSonatypeProperty,
                placeSwitchAtTheStart = true
            ) { publishToCentralSonatypeProperty = it }
            if (publishToCentralSonatypeProperty) {
                SwitchWithLabel(
                    "Add 'uploadSonatypePublication' root project task (required for Central Sonatype publishing)",
                    includeCentralSonatypeUploadingScriptProperty,
                    placeSwitchAtTheStart = true
                ) { includeCentralSonatypeUploadingScriptProperty = it }
            }
        }
        developersView.build()
        repositoriesView.build()
    }
}
