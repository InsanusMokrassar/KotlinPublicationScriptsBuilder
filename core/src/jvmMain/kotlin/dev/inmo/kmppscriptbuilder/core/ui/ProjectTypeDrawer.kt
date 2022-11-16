package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.models.JSProjectType
import dev.inmo.kmppscriptbuilder.core.models.JVMProjectType
import dev.inmo.kmppscriptbuilder.core.models.MultiplatformProjectType
import dev.inmo.kmppscriptbuilder.core.models.ProjectType
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

actual class ProjectTypeDrawer(
    private val projectTypeView: ProjectTypeView
) : Drawer<ProjectType> {
    @Composable
    override fun ProjectType.draw() {
        if (projectTypeView.projectType == this) {
            Button({}, Modifier.padding(8.dp, 0.dp)) {
                Text(name)
            }
        } else {
            OutlinedButton(
                {
                    projectTypeView.projectType = this
                },
                Modifier.padding(8.dp, 0.dp)
            ) {
                Text(name)
            }
        }
    }
}

actual fun ProjectTypeDrawerWithView(view: ProjectTypeView): ProjectTypeDrawer = ProjectTypeDrawer(projectTypeView = view)
