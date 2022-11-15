package dev.inmo.kmppscriptbuilder.core.ui.utils

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.elements.DefaultButton
import dev.inmo.jsuikit.elements.DefaultInput
import dev.inmo.jsuikit.elements.Divider
import dev.inmo.jsuikit.elements.Flex
import dev.inmo.jsuikit.elements.Icon
import dev.inmo.jsuikit.elements.Label
import dev.inmo.jsuikit.elements.drawAsFormInputPart
import dev.inmo.jsuikit.modifiers.UIKitButton
import dev.inmo.jsuikit.modifiers.UIKitFlex
import dev.inmo.jsuikit.modifiers.UIKitForm
import dev.inmo.jsuikit.modifiers.UIKitInverse
import dev.inmo.jsuikit.modifiers.UIKitMargin
import dev.inmo.jsuikit.modifiers.UIKitText
import dev.inmo.jsuikit.modifiers.UIKitUtility
import dev.inmo.jsuikit.modifiers.builder
import dev.inmo.jsuikit.modifiers.include
import dev.inmo.jsuikit.utils.Attrs
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Legend
import org.jetbrains.compose.web.dom.Text

@Composable
actual fun TitleText(text: String) {
    Legend(UIKitForm.Legend.builder()) {
        Text(text)
    }
}

@Composable
actual fun CommonText(text: String, onClick: (() -> Unit)?) {
    Div(
        {
            onClick ?.let {
                this.onClick { _ ->
                    it()
                }
            }
        }
    ) {
        Text(text)
    }
}

@Composable
actual fun CommonTextField(
    presetText: String,
    hint: String,
    onFocusChanged: (Boolean) -> Unit,
    onChange: (String) -> Unit
) {
    DefaultInput(
        InputType.Text,
        presetText,
        false,
        placeholder = hint,
        attributesCustomizer = {
            onFocusIn { onFocusChanged(true) }
            onFocusOut { onFocusChanged(false) }
        },
        onChange = onChange
    )
}

@Composable
actual fun SwitchWithLabel(
    label: String,
    checked: Boolean,
    placeSwitchAtTheStart: Boolean,
    switchEnabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    DefaultButton(
        if (checked) {
            UIKitButton.Type.Primary
        } else {
            UIKitButton.Type.Default
        },
        disabled = !switchEnabled,
        onClick = {
            onCheckedChange(!checked)
        },
        attributesCustomizer = {
            include(UIKitUtility.Inline)
        }
    ) {
        if (checked) {
            Icon.App.Check.drawAsFormInputPart(UIKitInverse.Light)
        }
        Text(label)
    }
}

@Composable
actual fun <T> ButtonsPanel(data: Iterable<T>, itemDrawer: @Composable (T) -> Unit) {
    Flex(UIKitFlex.Alignment.Vertical.Middle, UIKitMargin.Small) {
        data.forEach { itemDrawer(it) }
    }
}

@Composable
actual fun DefaultDivider() {
    Divider.Common()
}
