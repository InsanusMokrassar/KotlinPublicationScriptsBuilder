package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.elements.DefaultButton
import dev.inmo.jsuikit.modifiers.UIKitButton
import dev.inmo.jsuikit.modifiers.UIKitMargin
import dev.inmo.jsuikit.modifiers.UIKitTooltipModifier
import dev.inmo.jsuikit.modifiers.UIKitUtility
import dev.inmo.kmppscriptbuilder.core.models.GpgSigning
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer
import dev.inmo.kmppscriptbuilder.core.ui.utils.NoTransform

actual class GpgSigningOptionDrawer(
    private val mavenInfoView: MavenInfoView
) : Drawer<GpgSigning> {
    @Composable
    override fun GpgSigning.draw() {
        val tooltipModifier = UIKitTooltipModifier(
            when (this) {
                GpgSigning.Disabled -> "Signing will not be added"
                GpgSigning.Enabled -> "Signing will be always enabled"
                GpgSigning.Optional -> "Signing will be added, but disabled in case of absence 'signatory.keyId'"
            }
        )
        if (mavenInfoView.gpgSignProperty == this) {
            DefaultButton(name, UIKitButton.Type.Primary, UIKitButton.Size.Small, UIKitMargin.Small.Horizontal, UIKitUtility.NoTransform, UIKitUtility.Border.Rounded, tooltipModifier)
        } else {
            DefaultButton(name, UIKitButton.Type.Default, UIKitButton.Size.Small, UIKitMargin.Small.Horizontal, UIKitUtility.NoTransform, UIKitUtility.Border.Rounded, tooltipModifier) {
                mavenInfoView.gpgSignProperty = this
            }
        }
    }
}

actual fun GpgSigningOptionDrawerWithView(view: MavenInfoView): GpgSigningOptionDrawer = GpgSigningOptionDrawer(mavenInfoView = view)
