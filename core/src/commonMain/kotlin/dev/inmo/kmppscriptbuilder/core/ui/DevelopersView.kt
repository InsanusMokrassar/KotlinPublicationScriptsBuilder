package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.Developer

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
    var developers = mutableStateListOf<Developer>()

    override val addItemText: String = "Add developer"
    override val removeItemText: String = "Remove developer"

    override fun createItem(): DeveloperState = DeveloperState()

}
