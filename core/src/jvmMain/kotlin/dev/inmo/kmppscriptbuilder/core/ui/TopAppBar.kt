package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.models.Config
import dev.inmo.kmppscriptbuilder.core.utils.exportGradle
import dev.inmo.kmppscriptbuilder.core.utils.openNewConfig
import dev.inmo.kmppscriptbuilder.core.utils.saveAs
import dev.inmo.kmppscriptbuilder.core.utils.saveConfig

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

@Composable
actual fun TopAppBar(
    config: Config,
    saveAvailable: Boolean,
    onSaveAvailable: (Boolean) -> Unit,
    onNewConfig: (Config) -> Unit
) {
    TopAppBar(
        @Composable {
            Text("Kotlin publication scripts builder", Modifier.clickable { println(config) })
        },
        actions = {
            createIcon("Open file", "images/open_file.svg") {
                openNewConfig(onNewConfig)
            }

            if (saveAvailable) {
                createIcon("Save", "images/save_file.svg") {
                    saveConfig(config)
                }
            }

            if (saveAvailable) {
                createIcon("Export Gradle script", "images/export_gradle.svg") {
                    exportGradle(config)
                }
            }

            createIcon("Save as", "images/save_as.svg") {
                if (saveAs(config)) {
                    onSaveAvailable(true)
                }
            }
        }
    )
}
