package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.*
import dev.inmo.kmppscriptbuilder.core.models.MavenPublishingRepository
import dev.inmo.kmppscriptbuilder.desktop.utils.CommonTextField

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
        CommonTextField(
            item.name,
            "Repository name"
        ) { item.name = it }
        CommonTextField(
            item.url,
            "Repository url"
        ) { item.url = it }
    }

}
