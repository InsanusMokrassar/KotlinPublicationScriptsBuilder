package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
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
                            itemsList.add(it.toLicenseState())
                            licenseSearchFilter = ""
                        }
                        Divider()
                    }
                }
            }
        }
    }
}
