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
import dev.inmo.kmppscriptbuilder.core.models.GpgSigning
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer
import dev.inmo.kmppscriptbuilder.core.ui.utils.SwitchWithLabel

actual object MavenInfoDrawer : Drawer<MavenInfoView> {
    @Composable
    private fun MavenInfoView.addGpgSigningButton(gpgSigning: GpgSigning) {
        if (gpgSignProperty == gpgSigning) {
            Button({}, Modifier.padding(8.dp)) {
                Text(gpgSigning.name)
            }
        } else {
            OutlinedButton(
                {
                    gpgSignProperty = gpgSigning
                },
                Modifier.padding(8.dp)
            ) {
                Text(gpgSigning.name)
            }
        }
    }
    override fun MavenInfoView.draw() {
        CommonTextField(
            projectNameProperty,
            "Public project name"
        ) { projectNameProperty = it }
        CommonTextField(
            projectDescriptionProperty,
            "Public project description"
        ) { projectDescriptionProperty = it }
        CommonTextField(
            projectUrlProperty,
            "Public project URL"
        ) { projectUrlProperty = it }
        CommonTextField(
            projectVcsUrlProperty,
            "Public project VCS URL (with .git)"
        ) { projectVcsUrlProperty = it }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Gpg Signing: ")
            addGpgSigningButton(GpgSigning.Disabled)
            addGpgSigningButton(GpgSigning.Optional)
            addGpgSigningButton(GpgSigning.Enabled)
        }

        SwitchWithLabel(
            "Include publication to MavenCentral",
            publishToMavenCentralProperty,
            placeSwitchAtTheStart = true
        ) { publishToMavenCentralProperty = it }
        developersView.init()
        repositoriesView.init()
    }
}
