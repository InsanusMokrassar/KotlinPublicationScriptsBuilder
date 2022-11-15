package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.elements.DefaultButton
import dev.inmo.jsuikit.elements.Divider
import dev.inmo.jsuikit.modifiers.UIKitButton
import dev.inmo.jsuikit.modifiers.UIKitMargin
import dev.inmo.jsuikit.modifiers.builder
import dev.inmo.jsuikit.utils.Attrs
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer
import org.jetbrains.compose.web.dom.Div

actual object LicensesDrawer : Drawer<LicensesView> {
    @Composable
    override fun LicensesView.draw() {
        dev.inmo.jsuikit.elements.List(
            licensesOffersToShow.value,
            Attrs {
                if (!searchFieldFocused.value) {
                    hidden()
                }
            }
        ) {
            DefaultButton(
                it.title,
                UIKitButton.Type.Text
            ) { _ ->
                licensesListState.add(it.toLicenseState())
                licenseSearchFilter = ""
            }
            Divider.Common()
        }

        DefaultButton("Add empty license", UIKitButton.Type.Primary, UIKitMargin.Small) {
            licensesListState.add(LicenseState())
        }

        licensesListState.forEach { license ->
            Div(UIKitMargin.Small.builder()) {
                CommonTextField(
                    license.id,
                    "License ID",
                ) { license.id = it }
                CommonTextField(
                    license.title,
                    "License title",
                ) { license.title = it }
                CommonTextField(
                    license.url ?: "",
                    "License URL",
                ) { license.url = it }
                DefaultButton("Remove", UIKitButton.Type.Danger, UIKitMargin.Small) {
                    licensesListState.remove(license)
                }
            }
        }
    }
}
