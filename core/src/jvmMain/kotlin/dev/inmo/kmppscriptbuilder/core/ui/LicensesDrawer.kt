package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

actual object LicensesDrawer : Drawer<LicensesView> {
    @Composable
    override fun LicensesView.draw() {
        if (searchFieldFocused.value) {
            Column {
                licensesOffersToShow.value.forEach {
                    Column(Modifier.padding(16.dp, 8.dp, 8.dp, 8.dp)) {
                        CommonText(it.title) {
                            licensesListState.add(it.toLicenseState())
                            licenseSearchFilter = ""
                        }
                        Divider()
                    }
                }
            }
        }

        Button({ licensesListState.add(LicenseState()) }, Modifier.padding(8.dp)) {
            CommonText("Add empty license")
        }

        licensesListState.forEach { license ->
            Column(Modifier.padding(8.dp)) {
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
                Button({ licensesListState.remove(license) }, Modifier.padding(8.dp)) {
                    CommonText("Remove",)
                }
            }
        }
    }
}
