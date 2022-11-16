package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.elements.DefaultButton
import dev.inmo.jsuikit.modifiers.UIKitButton
import dev.inmo.jsuikit.modifiers.UIKitMargin
import dev.inmo.jsuikit.modifiers.UIKitUtility
import dev.inmo.kmppscriptbuilder.core.models.MavenPublishingRepository
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer
import dev.inmo.kmppscriptbuilder.core.ui.utils.NoTransform

actual class RepositoryCredentialTypeDrawer(
    private val state: RepositoryState
) : Drawer<MavenPublishingRepository.CredentialsType> {
    @Composable
    override fun MavenPublishingRepository.CredentialsType.draw() {
        val name = when (this@draw) {
            is MavenPublishingRepository.CredentialsType.HttpHeaderCredentials -> "Headers"
            MavenPublishingRepository.CredentialsType.Nothing -> "No"
            is MavenPublishingRepository.CredentialsType.UsernameAndPassword -> "Username and password"
        }
        if (state.credsType == this) {
            DefaultButton(name, UIKitButton.Type.Primary, UIKitButton.Size.Small, UIKitMargin.Small.Horizontal, UIKitUtility.NoTransform, UIKitUtility.Border.Rounded)
        } else {
            DefaultButton(name, UIKitButton.Type.Default, UIKitButton.Size.Small, UIKitMargin.Small.Horizontal, UIKitUtility.NoTransform, UIKitUtility.Border.Rounded) {
                state.credsType = this
            }
        }
    }
}

actual fun RepositoryCredentialTypeDrawerWithState(repositoryState: RepositoryState): RepositoryCredentialTypeDrawer = RepositoryCredentialTypeDrawer(repositoryState)
