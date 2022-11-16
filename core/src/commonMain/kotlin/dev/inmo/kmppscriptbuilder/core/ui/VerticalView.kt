package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import dev.inmo.kmppscriptbuilder.core.ui.utils.DefaultBox

abstract class VerticalView(protected val title: String) : View() {
    abstract val content: @Composable () -> Unit
    @Composable
    override fun build() {
        DefaultBox {
            DrawVertically(title, content)
        }
    }
}
