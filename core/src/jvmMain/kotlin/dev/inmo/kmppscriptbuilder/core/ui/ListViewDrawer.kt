package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.DefaultBox
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

actual class ListViewDrawer<T> : Drawer<ListView<T>> {
    @Composable
    override fun ListView<T>.draw() {
        itemsList.forEach { item ->
            DefaultBox {
                buildView(item)
                OutlinedButton({ itemsList.remove(item) }, Modifier.padding(8.dp)) {
                    CommonText(removeItemText,)
                }
            }
            Button({ itemsList.add(createItem()) }) {
                CommonText(addItemText,)
            }
        }
        if (itemsList.isEmpty()) {
            Button({ itemsList.add(createItem()) }) {
                CommonText(addItemText,)
            }
        }
    }
}
