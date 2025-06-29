package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

actual object LicensesDrawer : Drawer<LicensesView> {
    @Composable
    override fun LicensesView.draw() {
        val internalFocusState = remember { mutableStateOf(false) }
        if (searchFieldFocused.value || internalFocusState.value) {
        }
        Column(
            Modifier
                .let {
                    if (searchFieldFocused.value) {
                        it.heightIn(max = 128.dp)
                    } else {
                        it.height(0.dp)
                    }
                }
                .verticalScroll(rememberScrollState())
        ) {
            licensesOffersToShow.value.forEach {
                Column(Modifier.padding(16.dp, 8.dp, 8.dp, 8.dp)) {
                    CommonText(it.title) {
                        itemsList.add(it.toLicenseState())
                        licenseSearchFilter = ""
                        internalFocusState.value = false
                    }
                    Divider()
                }
            }
        }
    }
}
