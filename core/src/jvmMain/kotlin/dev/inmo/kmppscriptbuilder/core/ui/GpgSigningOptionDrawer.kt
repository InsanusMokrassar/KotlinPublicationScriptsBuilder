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

actual class GpgSigningOptionDrawer(
    private val mavenInfoView: MavenInfoView
) : Drawer<GpgSigning> {
    @Composable
    override fun GpgSigning.draw() {
        if (mavenInfoView.gpgSignProperty == this) {
            Button({}, Modifier.padding(8.dp, 0.dp)) {
                Text(name)
            }
        } else {
            OutlinedButton(
                {
                    mavenInfoView.gpgSignProperty = this
                },
                Modifier.padding(8.dp, 0.dp)
            ) {
                Text(name)
            }
        }
    }
}

actual fun GpgSigningOptionDrawerWithView(view: MavenInfoView): GpgSigningOptionDrawer = GpgSigningOptionDrawer(mavenInfoView = view)
