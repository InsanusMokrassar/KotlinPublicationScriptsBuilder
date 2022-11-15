package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.JSProjectType
import dev.inmo.kmppscriptbuilder.core.models.JVMProjectType
import dev.inmo.kmppscriptbuilder.core.models.MultiplatformProjectType
import dev.inmo.kmppscriptbuilder.core.models.ProjectType
import dev.inmo.kmppscriptbuilder.core.ui.utils.ButtonsPanel
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

expect class ProjectTypeDrawer : Drawer<ProjectType>
expect fun ProjectTypeDrawerWithView(view: ProjectTypeView): ProjectTypeDrawer

class ProjectTypeView : VerticalView("Project type") {
    var projectType by mutableStateOf<ProjectType>(MultiplatformProjectType)
    private val typeDrawer = ProjectTypeDrawerWithView(this)

    override val content: @Composable () -> Unit = {
        ButtonsPanel(
            MultiplatformProjectType,
            JVMProjectType,
            JSProjectType
        ) {
            with(typeDrawer) {
                with (it) {
                    draw()
                }
            }
        }
    }
}
