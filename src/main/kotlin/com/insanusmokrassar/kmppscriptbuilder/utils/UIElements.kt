package com.insanusmokrassar.kmppscriptbuilder.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val commonTextFieldTextStyle = TextStyle(
    fontSize = 12.sp
)

@Composable
inline fun TitleText(text: String) = Text(
    text, Modifier.padding(0.dp, 8.dp), fontSize = 18.sp
)

@Composable
inline fun CommonText(text: String, modifier: Modifier = Modifier) = Text(
    text, modifier = modifier
)

@Composable
inline fun CommonTextField(presetText: String, hint: String, noinline onChange: (String) -> Unit) = OutlinedTextField(
    presetText,
    onChange,
    Modifier.fillMaxWidth(),
    singleLine = true,
    label = {
        CommonText(hint)
    }
)

@Composable
inline fun SwitchWithLabel(
    label: String,
    checked: Boolean,
    placeSwitchAtTheStart: Boolean = false,
    switchEnabled: Boolean = true,
    modifier: Modifier = Modifier.padding(0.dp, 8.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    switchModifier: Modifier = Modifier.padding(8.dp, 0.dp),
    noinline onCheckedChange: (Boolean) -> Unit
) = Row(modifier, horizontalArrangement, verticalAlignment) {
    val switchCreator = @Composable {
        Switch(checked, onCheckedChange, switchModifier, enabled = switchEnabled)
    }
    if (placeSwitchAtTheStart) {
        switchCreator()
    }
    CommonText(label, Modifier.align(Alignment.CenterVertically).clickable {  })
    if (!placeSwitchAtTheStart) {
        switchCreator()
    }
}