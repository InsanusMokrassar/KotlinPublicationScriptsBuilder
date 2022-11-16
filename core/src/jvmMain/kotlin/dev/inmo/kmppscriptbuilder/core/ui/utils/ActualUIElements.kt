package dev.inmo.kmppscriptbuilder.core.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val commonTextFieldTextStyle = TextStyle(
    fontSize = 12.sp
)

@Composable
actual fun SwitchWithLabel(
    label: String,
    checked: Boolean,
    placeSwitchAtTheStart: Boolean,
    switchEnabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(Modifier.padding(0.dp, 8.dp).clickable { onCheckedChange(!checked) }, Arrangement.Start, Alignment.Top) {
        val switchCreator = @Composable {
            Switch(checked, null, Modifier.padding(8.dp, 0.dp), enabled = switchEnabled)
        }
        if (placeSwitchAtTheStart) {
            switchCreator()
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterVertically)) {
            CommonText(label)
        }
        if (!placeSwitchAtTheStart) {
            switchCreator()
        }
    }
}

@Composable
actual fun CommonTextField(
    presetText: String,
    hint: String?,
    onFocusChanged: (Boolean) -> Unit,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        presetText,
        onChange,
        Modifier.fillMaxWidth().onFocusChanged {
            onFocusChanged(it.isFocused)
        },
        singleLine = true,
        label = hint ?.let {
            {
                CommonText(hint)
            }
        }
    )
}

@Composable
actual fun CommonText(text: String, onClick: (() -> Unit)?) {
    Text(text, modifier = Modifier.run { onClick ?.let { clickable(onClick = it) } ?: this })
}

@Composable
actual fun TitleText(text: String) {
    Text(
        text, Modifier.padding(0.dp, 8.dp), fontSize = 18.sp
    )
}

@Composable
actual fun <T> ButtonsPanel(
    title: String,
    data: Iterable<T>,
    itemDrawer: @Composable (T) -> Unit
) {
    Row {
        Text(title, Modifier.padding(8.dp))
        data.forEach { itemDrawer(it) }
    }
}

@Composable
actual fun DefaultDivider() {
    Divider()
}

@Composable
actual fun DefaultSmallVerticalMargin() {
    Spacer(Modifier.padding(0.dp, 4.dp))
}

@Composable
actual fun DefaultBox(block: @Composable () -> Unit) {
    Column(Modifier.padding(8.dp)) {
        block()
    }
}
