package dev.inmo.kmppscriptbuilder.desktop.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.models.*
import dev.inmo.kmppscriptbuilder.desktop.utils.VerticalView

class ProjectTypeView : VerticalView("Project type") {
    var projectType by mutableStateOf<ProjectType>(MultiplatformProjectType)

    @Composable
    private fun addProjectTypeButton(newProjectType: ProjectType) {
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

    override val content: @Composable ColumnScope.() -> Unit = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            addProjectTypeButton(MultiplatformProjectType)
            addProjectTypeButton(JVMProjectType)
            addProjectTypeButton(JSProjectType)
        }
    }
}
