package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable

expect abstract class View() {
    @Composable
    abstract fun build()
}

@Composable
expect fun View.DrawVertically(title: String, block: @Composable () -> Unit)

@Composable
fun <T : View> T.init(): T = apply {
    build()
}
