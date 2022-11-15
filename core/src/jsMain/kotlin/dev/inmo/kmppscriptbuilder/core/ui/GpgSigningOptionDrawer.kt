package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.elements.DefaultButton
import dev.inmo.jsuikit.modifiers.UIKitButton
import dev.inmo.jsuikit.modifiers.UIKitMargin
import dev.inmo.kmppscriptbuilder.core.models.GpgSigning
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

actual class GpgSigningOptionDrawer(
    private val mavenInfoView: MavenInfoView
) : Drawer<GpgSigning> {
    @Composable
    override fun GpgSigning.draw() {
        if (mavenInfoView.gpgSignProperty == this) {
            DefaultButton(name, UIKitButton.Type.Primary)
        } else {
            DefaultButton(name, UIKitButton.Type.Default) {
                mavenInfoView.gpgSignProperty = this
            }
        }
    }
}

actual fun GpgSigningOptionDrawerWithView(view: MavenInfoView): GpgSigningOptionDrawer = GpgSigningOptionDrawer(mavenInfoView = view)
