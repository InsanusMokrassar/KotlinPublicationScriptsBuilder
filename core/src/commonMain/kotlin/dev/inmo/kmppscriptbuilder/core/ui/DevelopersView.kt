package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.Developer
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField

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
            itemsList.apply {
                clear()
                addAll(value.map { it.toDeveloperState() })
            }
        }

    override val addItemText: String = "Add developer"
    override val removeItemText: String = "Remove developer"

    override fun createItem(): DeveloperState = DeveloperState()
    @Composable
    override fun buildView(item: DeveloperState) {
        CommonTextField(
            item.id,
            "Developer username",
        ) { item.id = it }
        CommonTextField(
            item.name,
            "Developer name",
        ) { item.name = it }
        CommonTextField(
            item.eMail,
            "Developer E-Mail",
        ) { item.eMail = it }
    }
}
