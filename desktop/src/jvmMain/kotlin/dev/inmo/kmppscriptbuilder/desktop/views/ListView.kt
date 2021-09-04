package dev.inmo.kmppscriptbuilder.desktop.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.desktop.utils.CommonText
import dev.inmo.kmppscriptbuilder.desktop.utils.VerticalView

abstract class ListView<T>(title: String) : VerticalView(title) {
    protected val itemsList = mutableStateListOf<T>()

    protected open val addItemText: String = "Add"
    protected open val removeItemText: String = "Remove"

    protected abstract fun createItem(): T
    @Composable
    protected abstract fun buildView(item: T)

    override val content: @Composable ColumnScope.() -> Unit = {
        Button({ itemsList.add(createItem()) }) {
            CommonText(addItemText)
        }
        itemsList.forEach { item ->
            Column(Modifier.padding(8.dp)) {
                buildView(item)
                Button({ itemsList.remove(item) }, Modifier.padding(8.dp)) {
                    CommonText(removeItemText)
                }
            }
        }
    }
}
