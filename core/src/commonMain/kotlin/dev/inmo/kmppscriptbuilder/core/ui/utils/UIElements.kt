package dev.inmo.kmppscriptbuilder.core.ui.utils

import androidx.compose.runtime.Composable

@Composable
expect fun TitleText(text: String)

@Composable
expect fun CommonText(text: String)

@Composable
expect fun CommonTextField(presetText: String, hint: String, onChange: (String) -> Unit)

@Composable
expect fun SwitchWithLabel(
    label: String,
    checked: Boolean,
    placeSwitchAtTheStart: Boolean = false,
    switchEnabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
)
