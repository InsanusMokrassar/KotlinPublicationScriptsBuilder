package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.elements.DefaultButton
import dev.inmo.jsuikit.modifiers.UIKitButton
import dev.inmo.jsuikit.modifiers.UIKitMargin
import dev.inmo.kmppscriptbuilder.core.models.ProjectType
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

actual class ProjectTypeDrawer(
    private val projectTypeView: ProjectTypeView
) : Drawer<ProjectType> {
    @Composable
    override fun ProjectType.draw() {
        if (projectTypeView.projectType == this) {
            DefaultButton(name, UIKitButton.Type.Primary)
        } else {
            DefaultButton(name, UIKitButton.Type.Default) {
                projectTypeView.projectType = this
            }
        }
    }
}

actual fun ProjectTypeDrawerWithView(view: ProjectTypeView): ProjectTypeDrawer = ProjectTypeDrawer(projectTypeView = view)
