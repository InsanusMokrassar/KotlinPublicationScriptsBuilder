package dev.inmo.kmppscriptbuilder.core.ui.utils

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.elements.DefaultButton
import dev.inmo.jsuikit.elements.DefaultInput
import dev.inmo.jsuikit.elements.Divider
import dev.inmo.jsuikit.elements.Flex
import dev.inmo.jsuikit.elements.Icon
import dev.inmo.jsuikit.elements.drawAsFormInputPart
import dev.inmo.jsuikit.modifiers.UIKitButton
import dev.inmo.jsuikit.modifiers.UIKitFlex
import dev.inmo.jsuikit.modifiers.UIKitForm
import dev.inmo.jsuikit.modifiers.UIKitInverse
import dev.inmo.jsuikit.modifiers.UIKitMargin
import dev.inmo.jsuikit.modifiers.UIKitUtility
import dev.inmo.jsuikit.modifiers.attrsBuilder
import dev.inmo.jsuikit.modifiers.builder
import dev.inmo.jsuikit.modifiers.include
import kotlinx.browser.window
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
    hint: String?,
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
            onFocusOut {
                window.setTimeout( // avoid immediate hiding of potential interface data with additional delay
                    { onFocusChanged(false) },
                    100
                )
            }
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
            include(UIKitUtility.Inline, UIKitUtility.NoTransform, UIKitUtility.Border.Rounded)
        }
    ) {
        if (checked) {
            Icon.App.Check.drawAsFormInputPart(UIKitInverse.Light)
        }
        Text(label)
    }
}

@Composable
actual fun <T> ButtonsPanel(
    title: String,
    data: Iterable<T>,
    itemDrawer: @Composable (T) -> Unit
) {
    Flex(UIKitFlex.Alignment.Vertical.Middle, UIKitMargin.Small) {
        Div(UIKitMargin.Small.Horizontal.builder()) { Text(title) }
        data.forEach { itemDrawer(it) }
    }
}

@Composable
actual fun DefaultDivider() {
    Divider.Common()
}

@Composable
actual fun DefaultSmallVerticalMargin() {
    Div(UIKitMargin.Small.Top.builder())
}

@Composable
actual fun DefaultBox(block: @Composable () -> Unit) {
    Div(attrsBuilder(UIKitMargin.Small.Horizontal, UIKitMargin.Small.Vertical)) {
        block()
    }
}
