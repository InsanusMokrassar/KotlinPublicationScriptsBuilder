package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.MavenPublishingRepository
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.DefaultSmallVerticalMargin

class RepositoryState(
    name: String = "",
    url: String = ""
) {
    var name: String by mutableStateOf(name)
    var url: String by mutableStateOf(url)

    fun toRepository() = MavenPublishingRepository(name, url)
}

private fun MavenPublishingRepository.toRepositoryState() = RepositoryState(name, url)

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
    }

}
