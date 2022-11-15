package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.Config
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

expect object BuilderViewDrawer : Drawer<BuilderView>

class BuilderView : View() {
    internal val projectTypeView = ProjectTypeView()
    internal val mavenInfoView = MavenInfoView()
    internal val licensesView = LicensesView()

    internal var saveAvailableState by mutableStateOf(false)
    var config: Config
        get() = Config(licensesView.licenses, mavenInfoView.mavenConfig, projectTypeView.projectType)
        set(value) {
            licensesView.licenses = value.licenses
            mavenInfoView.mavenConfig = value.mavenConfig
            projectTypeView.projectType = value.type
            saveAvailableState = true
        }

    @Composable
    override fun build() {
        with(BuilderViewDrawer) { draw() }
    }
}
