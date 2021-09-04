package dev.inmo.kmppscriptbuilder.desktop.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.models.Config
import dev.inmo.kmppscriptbuilder.desktop.utils.*

class BuilderView : View() {
    private val projectTypeView = ProjectTypeView()
    private val mavenInfoView = MavenInfoView()
    private val licensesView = LicensesView()
    private var saveAvailableState by mutableStateOf(false)

    override val defaultModifier: Modifier = Modifier.fillMaxSize()

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
        Box(Modifier.fillMaxSize()) {
            Column() {
                TopAppBar(
                    @Composable {
                        CommonText("Kotlin publication scripts builder", Modifier.clickable { println(config) })
                    },
                    actions = {
                        IconButton(
                            {
                                loadConfig()?.also {
                                    config = it
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource("images/open_file.svg"),
                                contentDescription = "Open file"
                            )
                        }

                        if (saveAvailableState) {
                            IconButton(
                                {
                                    saveConfig(config)
                                }
                            ) {
                                Image(
                                    painter = painterResource("images/save_file.svg"),
                                    contentDescription = "Save file"
                                )
                            }
                        }

                        if (saveAvailableState) {
                            IconButton(
                                {
                                    exportGradle(config)
                                }
                            ) {
                                Image(
                                    painter = painterResource("images/export_gradle.svg"),
                                    contentDescription = "Export Gradle script"
                                )
                            }
                        }

                        IconButton(
                            {
                                if (saveAs(config)) {
                                    saveAvailableState = true
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource("images/save_as.svg"),
                                contentDescription = "Export Gradle script"
                            )
                        }
                    }
                )
                Column(Modifier.padding(8.dp)) {
                    projectTypeView.init()
                    Divider()
                    licensesView.init()
                    Divider()
                    mavenInfoView.init()
                }
            }
        }
    }
}
