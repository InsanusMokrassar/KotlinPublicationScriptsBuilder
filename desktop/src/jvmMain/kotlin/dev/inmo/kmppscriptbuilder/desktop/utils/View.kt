package dev.inmo.kmppscriptbuilder.desktop.utils

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

abstract class View {
    protected open val defaultModifier = Modifier.fillMaxWidth().padding(8.dp)

    @Composable
    abstract fun build()
}

abstract class VerticalView(val title: String) : View() {
    abstract val content: @Composable ColumnScope.() -> Unit
    @Composable
    override fun build() {
        TitleText(title)

        Column(
            defaultModifier
        ) {
            content()
        }

        Spacer(Modifier.fillMaxWidth().height(8.dp))
    }
}

@Composable
fun <T : View> T.init(): T = apply {
    build()
}