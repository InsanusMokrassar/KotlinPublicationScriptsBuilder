package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.models.MavenPublishingRepository
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

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
            Button({}, Modifier.padding(8.dp, 0.dp)) {
                Text(name)
            }
        } else {
            OutlinedButton(
                {
                    state.credsType = this
                },
                Modifier.padding(8.dp, 0.dp)
            ) {
                Text(name)
            }
        }
    }
}

actual fun RepositoryCredentialTypeDrawerWithState(repositoryState: RepositoryState): RepositoryCredentialTypeDrawer = RepositoryCredentialTypeDrawer(repositoryState)
