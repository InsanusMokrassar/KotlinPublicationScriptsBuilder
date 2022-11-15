package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

actual class ListViewDrawer<T> : Drawer<ListView<T>> {
    override fun ListView<T>.draw() {
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
