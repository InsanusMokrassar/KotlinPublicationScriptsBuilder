package dev.inmo.kmppscriptbuilder.core.ui.utils

import androidx.compose.runtime.Composable

interface Drawer<T> {
    @Composable
    fun T.draw()
}
