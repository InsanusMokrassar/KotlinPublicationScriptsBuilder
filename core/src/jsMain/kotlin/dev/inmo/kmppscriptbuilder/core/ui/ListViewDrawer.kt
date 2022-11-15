package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.elements.DefaultButton
import dev.inmo.jsuikit.modifiers.UIKitButton
import dev.inmo.jsuikit.modifiers.UIKitMargin
import dev.inmo.jsuikit.modifiers.UIKitUtility
import dev.inmo.jsuikit.modifiers.builder
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer
import dev.inmo.kmppscriptbuilder.core.ui.utils.NoTransform
import org.jetbrains.compose.web.dom.Div

actual class ListViewDrawer<T> : Drawer<ListView<T>> {
    @Composable
    override fun ListView<T>.draw() {
        DefaultButton(addItemText, UIKitButton.Type.Primary, UIKitUtility.NoTransform, UIKitUtility.Border.Rounded ) { itemsList.add(createItem()) }
        itemsList.forEach { item ->
            Div(UIKitMargin.Small.builder()) {
                buildView(item)
                DefaultButton(removeItemText, UIKitButton.Type.Default, UIKitMargin.Small, UIKitUtility.NoTransform, UIKitUtility.Border.Rounded) {
                    itemsList.remove(item)
                }
            }
        }
    }
}
