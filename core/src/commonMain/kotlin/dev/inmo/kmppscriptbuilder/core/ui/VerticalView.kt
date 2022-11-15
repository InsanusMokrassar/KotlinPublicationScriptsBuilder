package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable

abstract class VerticalView(protected val title: String) : View() {
    abstract val content: @Composable () -> Unit
    @Composable
    override fun build() {
        DrawVertically(title, content)
    }
}
