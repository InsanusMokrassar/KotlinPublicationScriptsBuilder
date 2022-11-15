package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

expect class ListViewDrawer<T>() : Drawer<ListView<T>>

abstract class ListView<T>(title: String) : VerticalView(title) {
    internal val itemsList = mutableStateListOf<T>()

    internal open val addItemText: String = "Add"
    internal open val removeItemText: String = "Remove"

    internal abstract fun createItem(): T
    @Composable
    internal abstract fun buildView(item: T)

    protected val drawer = ListViewDrawer<T>()

    override val content: () -> Unit = {
        with(drawer) {
            draw()
        }
    }
}
