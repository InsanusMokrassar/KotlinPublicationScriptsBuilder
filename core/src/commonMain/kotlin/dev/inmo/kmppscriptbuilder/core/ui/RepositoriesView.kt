package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.MavenPublishingRepository
import dev.inmo.kmppscriptbuilder.core.ui.utils.ButtonsPanel
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.DefaultSmallVerticalMargin
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

class RepositoryState(
    name: String = "",
    url: String = "",
    credsType: MavenPublishingRepository.CredentialsType = MavenPublishingRepository.CredentialsType.UsernameAndPassword(name)
) {
    var name: String by mutableStateOf(name)
    var url: String by mutableStateOf(url)
    var credsType by mutableStateOf(credsType)

    fun toRepository() = MavenPublishingRepository(name, url)
}

private fun MavenPublishingRepository.toRepositoryState() = RepositoryState(name, url)

expect class RepositoryCredentialTypeDrawer : Drawer<MavenPublishingRepository.CredentialsType>
expect fun RepositoryCredentialTypeDrawerWithState(repositoryState: RepositoryState): RepositoryCredentialTypeDrawer

class RepositoriesView : ListView<RepositoryState>("Repositories info") {
    var repositories: List<MavenPublishingRepository>
        get() = itemsList.map { it.toRepository() }
        set(value) {
            itemsList.clear()
            itemsList.addAll(
                value.map { it.toRepositoryState() }
            )
        }

    override val addItemText: String = "Add repository"
    override val removeItemText: String = "Remove repository"

    override fun createItem(): RepositoryState = RepositoryState()

    @Composable
    override fun buildView(item: RepositoryState) {
        val credsTypesDrawer = remember {
            RepositoryCredentialTypeDrawerWithState(item)
        }

        CommonText("Repository name")
        CommonTextField(
            item.name,
            "This name will be used to identify repository in gradle"
        ) { item.name = it }
        DefaultSmallVerticalMargin()
        CommonText("Repository url")
        CommonTextField(
            item.url,
            "For example: https://repo.maven.apache.org/maven2/"
        ) { item.url = it }

        ButtonsPanel(
            "Credentials type",
            MavenPublishingRepository.CredentialsType.Nothing.takeIf { item.credsType != it } ?: item.credsType,
            MavenPublishingRepository.CredentialsType.UsernameAndPassword(item.name).takeIf { item.credsType !is MavenPublishingRepository.CredentialsType.UsernameAndPassword } ?: item.credsType,
            MavenPublishingRepository.CredentialsType.HttpHeaderCredentials("Authorization", "${item.name.uppercase()}_TOKEN").takeIf { item.credsType !is MavenPublishingRepository.CredentialsType.HttpHeaderCredentials } ?: item.credsType,
        ) {
            with(credsTypesDrawer) {
                with(item.credsType) {
                    draw()
                }
            }
        }

        when (val credsType = item.credsType) {
            is MavenPublishingRepository.CredentialsType.HttpHeaderCredentials -> {
                CommonText("Header name")
                CommonTextField(credsType.headerName) {
                    item.credsType = credsType.copy(headerName = it)
                }
                DefaultSmallVerticalMargin()

                CommonText("Property name")
                CommonTextField(credsType.headerValueProperty) {
                    item.credsType = credsType.copy(headerValueProperty = it)
                }
            }
            MavenPublishingRepository.CredentialsType.Nothing -> {
                CommonText("No parameters for absence of credentials")
            }
            is MavenPublishingRepository.CredentialsType.UsernameAndPassword -> {
                CommonText("Username property name")
                CommonTextField(credsType.usernameProperty) {
                    item.credsType = credsType.copy(usernameProperty = it)
                }
                DefaultSmallVerticalMargin()

                CommonText("Password property name")
                CommonTextField(credsType.passwordProperty) {
                    item.credsType = credsType.copy(passwordProperty = it)
                }
            }
        }
    }

}
