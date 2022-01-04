package dev.inmo.kmppscriptbuilder.desktop.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun createIcon(
        tooltip: String,
        resource: String,
        onClick: () -> Unit
    ) {
        TooltipArea(
            tooltip = {
                Surface(
                    modifier = Modifier.shadow(4.dp),
                    color = MaterialTheme.colors.primarySurface,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(tooltip, modifier = Modifier.padding(10.dp), color = MaterialTheme.colors.onPrimary)
                }
            }
        ) {
            IconButton(onClick) {
                Image(
                    painter = painterResource(resource),
                    contentDescription = tooltip
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun build() {
        Box(Modifier.fillMaxSize()) {
            Column() {
                TopAppBar(
                    @Composable {
                        CommonText("Kotlin publication scripts builder", Modifier.clickable { println(config) })
                    },
                    actions = {
                        createIcon("Open file", "images/open_file.svg") {
                            loadConfig()?.also {
                                config = it
                            }
                        }

                        if (saveAvailableState) {
                            createIcon("Save", "images/save_file.svg") {
                                saveConfig(config)
                            }
                        }

                        if (saveAvailableState) {
                            createIcon("Export Gradle script", "images/export_gradle.svg") {
                                exportGradle(config)
                            }
                        }

                        createIcon("Save as", "images/save_as.svg") {
                            if (saveAs(config)) {
                                saveAvailableState = true
                            }
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
