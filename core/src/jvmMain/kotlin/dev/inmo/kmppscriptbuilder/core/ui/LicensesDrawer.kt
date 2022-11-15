package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

actual object LicensesDrawer : Drawer<LicensesView> {
    override fun LicensesView.draw() {
        CommonTextField(licenseSearchFilter, "Search filter") { filterText ->
            licenseSearchFilter = filterText
            licensesOffersToShow.clear()
            if (licenseSearchFilter.isNotEmpty()) {
                licensesOffersToShow.addAll(
                    availableLicensesState.filter { filterText.all { symbol -> symbol.lowercaseChar() in it.title } }
                )
            }
        }
        Column {
            licensesOffersToShow.forEach {
                Column(Modifier.padding(16.dp, 8.dp, 8.dp, 8.dp)) {
                    CommonText(it.title) {
                        licensesListState.add(it.toLicenseState())
                        licenseSearchFilter = ""
                        licensesOffersToShow.clear()
                    }
                    Divider()
                }
            }
        }
        Button({ licensesListState.add(LicenseState()) }, Modifier.padding(8.dp)) {
            CommonText("Add empty license",)
        }
        licensesListState.forEach { license ->
            Column(Modifier.padding(8.dp)) {
                CommonTextField(
                    license.id,
                    "License ID"
                ) { license.id = it }
                CommonTextField(
                    license.title,
                    "License title"
                ) { license.title = it }
                CommonTextField(
                    license.url ?: "",
                    "License URL"
                ) { license.url = it }
                Button({ licensesListState.remove(license) }, Modifier.padding(8.dp)) {
                    CommonText("Remove",)
                }
            }
        }
    }
}
