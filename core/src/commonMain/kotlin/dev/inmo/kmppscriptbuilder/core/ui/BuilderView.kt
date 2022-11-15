package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.Config
import dev.inmo.kmppscriptbuilder.core.ui.utils.DefaultDivider

@Composable
expect fun TopAppBar(
    config: Config,
    saveAvailable: Boolean,
    onSaveAvailable: (Boolean) -> Unit,
    onNewConfig: (Config) -> Unit
)

class BuilderView : View() {
    internal val projectTypeView by mutableStateOf(ProjectTypeView())
    internal val mavenInfoView by mutableStateOf(MavenInfoView())
    internal val licensesView by mutableStateOf(LicensesView())

    internal var saveAvailableState by mutableStateOf(false)
    var config: Config
        get() {
            return Config(licensesView.licenses, mavenInfoView.mavenConfig, projectTypeView.projectType)
        }
        set(value) {
            licensesView.licenses = value.licenses
            mavenInfoView.mavenConfig = value.mavenConfig
            projectTypeView.projectType = value.type
            saveAvailableState = true
        }

    @Composable
    override fun build() {
        TopAppBar(
            config,
            saveAvailableState,
            {
                saveAvailableState = it
            }
        ) {
            config = it
        }

        projectTypeView.build()
        DefaultDivider()
        licensesView.build()
        DefaultDivider()
        mavenInfoView.build()
    }
}
