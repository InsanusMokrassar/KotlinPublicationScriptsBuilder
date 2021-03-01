package dev.inmo.kmppscriptbuilder.desktop.views

import androidx.compose.runtime.*
import dev.inmo.kmppscriptbuilder.core.models.Developer
import dev.inmo.kmppscriptbuilder.desktop.utils.*

class DeveloperState(
    id: String = "",
    name: String = "",
    eMail: String = ""
) {
    var id: String by mutableStateOf(id)
    var name: String by mutableStateOf(name)
    var eMail: String by mutableStateOf(eMail)

    fun toDeveloper() = Developer(id, name, eMail)
}

private fun Developer.toDeveloperState() = DeveloperState(id, name, eMail)

class DevelopersView : ListView<DeveloperState>("Developers info") {
    var developers: List<Developer>
        get() = itemsList.map { it.toDeveloper() }
        set(value) {
            itemsList.clear()
            itemsList.addAll(
                value.map { it.toDeveloperState() }
            )
        }

    override val addItemText: String = "Add developer"
    override val removeItemText: String = "Remove developer"

    override fun createItem(): DeveloperState = DeveloperState()
    @Composable
    override fun buildView(item: DeveloperState) {
        CommonTextField(
            item.id,
            "Developer username"
        ) { item.id = it }
        CommonTextField(
            item.name,
            "Developer name"
        ) { item.name = it }
        CommonTextField(
            item.eMail,
            "Developer E-Mail"
        ) { item.eMail = it }
    }

}
