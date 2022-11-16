package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.modifiers.UIKitForm
import dev.inmo.jsuikit.modifiers.builder
import dev.inmo.kmppscriptbuilder.core.ui.utils.DefaultSmallVerticalMargin
import org.jetbrains.compose.web.dom.Legend
import org.jetbrains.compose.web.dom.Text

actual abstract class View {
    @Composable
    actual abstract fun build()
}

@Composable
actual fun View.DrawVertically(title: String, block: @Composable () -> Unit) {
    Legend(UIKitForm.Legend.builder()) {
        Text(title)
    }
    DefaultSmallVerticalMargin()
    block()
    DefaultSmallVerticalMargin()
}
