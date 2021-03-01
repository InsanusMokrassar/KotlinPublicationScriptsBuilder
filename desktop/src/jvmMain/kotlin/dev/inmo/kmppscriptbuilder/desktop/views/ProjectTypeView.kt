package dev.inmo.kmppscriptbuilder.desktop.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.models.*
import dev.inmo.kmppscriptbuilder.desktop.utils.VerticalView

class ProjectTypeView : VerticalView("Project type") {
    private var projectTypeState by mutableStateOf<Boolean>(false)
    private val calculatedProjectType: ProjectType
        get() = if (projectTypeState) JVMProjectType else MultiplatformProjectType
    var projectType: ProjectType
        get() = calculatedProjectType
        set(value) {
            projectTypeState = value == JVMProjectType
        }

    override val content: @Composable ColumnScope.() -> Unit = {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("Multiplatform", Modifier.alignByBaseline())
            Switch(
                projectTypeState,
                { projectTypeState = it },
                Modifier.padding(4.dp, 0.dp)
            )
            Text("JVM", Modifier.alignByBaseline())
        }
    }
}
