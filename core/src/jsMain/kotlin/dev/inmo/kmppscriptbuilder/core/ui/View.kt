package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable

actual abstract class View {
    @Composable
    actual abstract fun build()
}

@Composable
actual fun View.DrawVertically(title: String, block: @Composable () -> Unit) {
}
