package dev.inmo.kmppscriptbuilder.core.ui.utils

import androidx.compose.runtime.Composable

@Composable
expect fun TitleText(text: String)

@Composable
expect fun CommonText(text: String, onClick: (() -> Unit)? = null)

@Composable
expect fun CommonTextField(
    presetText: String,
    hint: String? = null,
    onFocusChanged: (Boolean) -> Unit = {},
    onChange: (String) -> Unit
)

@Composable
expect fun SwitchWithLabel(
    label: String,
    checked: Boolean,
    placeSwitchAtTheStart: Boolean = false,
    switchEnabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
)

@Composable
expect fun <T> ButtonsPanel(
    title: String,
    data: Iterable<T>,
    itemDrawer: @Composable (T) -> Unit
)

@Composable
fun <T> ButtonsPanel(
    title: String,
    vararg data: T,
    itemDrawer: @Composable (T) -> Unit
) = ButtonsPanel(title, data.toList(), itemDrawer)

@Composable
expect fun DefaultDivider()

@Composable
expect fun DefaultSmallVerticalMargin()

@Composable
expect fun DefaultBox(block: @Composable () -> Unit)
