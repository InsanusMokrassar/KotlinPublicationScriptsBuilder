package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer
import dev.inmo.kmppscriptbuilder.core.utils.exportGradle
import dev.inmo.kmppscriptbuilder.core.utils.loadConfig
import dev.inmo.kmppscriptbuilder.core.utils.saveAs
import dev.inmo.kmppscriptbuilder.core.utils.saveConfig

actual object BuilderViewDrawer : Drawer<BuilderView> {
    override fun BuilderView.draw() {
        Box(Modifier.fillMaxSize()) {
            Column() {
                TopAppBar(
                    @Composable {
                        Text("Kotlin publication scripts builder", Modifier.clickable { println(config) })
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
