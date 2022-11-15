package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.MultiplatformProjectType
import dev.inmo.kmppscriptbuilder.core.models.ProjectType
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

expect object ProjectTypeDrawer : Drawer<ProjectTypeView>

class ProjectTypeView : VerticalView("Project type") {
    var projectType by mutableStateOf<ProjectType>(MultiplatformProjectType)

    override val content: @Composable () -> Unit = {
        with(ProjectTypeDrawer) { draw() }
    }
}
