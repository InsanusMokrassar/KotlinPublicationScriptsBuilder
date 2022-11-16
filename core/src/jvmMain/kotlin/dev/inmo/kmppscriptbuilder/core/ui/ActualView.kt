package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.ui.utils.DefaultSmallVerticalMargin
import dev.inmo.kmppscriptbuilder.core.ui.utils.TitleText

actual abstract class View {
    internal open val defaultModifier = Modifier.fillMaxWidth()
    @Composable
    actual abstract fun build()
}

@Composable
actual fun View.DrawVertically(
    title: String,
    block: @Composable () -> Unit
) {
    TitleText(title)
    DefaultSmallVerticalMargin()

    Column(defaultModifier) {
        block()
    }

    DefaultSmallVerticalMargin()
}
