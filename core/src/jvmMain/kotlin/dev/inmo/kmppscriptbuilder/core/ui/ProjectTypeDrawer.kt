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

actual object ProjectTypeDrawer : Drawer<ProjectTypeView> {
    @Composable
    private fun ProjectTypeView.addProjectTypeButton(newProjectType: ProjectType) {
        if (projectType == newProjectType) {
            Button({}, Modifier.padding(8.dp)) {
                Text(newProjectType.name)
            }
        } else {
            OutlinedButton(
                {
                    projectType = newProjectType
                },
                Modifier.padding(8.dp)
            ) {
                Text(newProjectType.name)
            }
        }
    }

    override fun ProjectTypeView.draw() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            addProjectTypeButton(MultiplatformProjectType)
            addProjectTypeButton(JVMProjectType)
            addProjectTypeButton(JSProjectType)
        }
    }
}
