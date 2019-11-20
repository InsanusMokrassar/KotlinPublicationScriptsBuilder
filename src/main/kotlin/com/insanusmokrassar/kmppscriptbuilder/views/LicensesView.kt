package com.insanusmokrassar.kmppscriptbuilder.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.insanusmokrassar.kmppscriptbuilder.models.License
import com.insanusmokrassar.kmppscriptbuilder.models.getLicenses
import com.insanusmokrassar.kmppscriptbuilder.utils.*
import io.ktor.client.HttpClient
import kotlinx.coroutines.*

private class LicenseState(
    id: String = "",
    title: String = "",
    url: String? = null
) {
    var id: String by mutableStateOf(id)
    var title: String by mutableStateOf(title)
    var url: String? by mutableStateOf(url)

    fun toLicense() = License(id, title, url)
}

private fun License.toLicenseState() = LicenseState(id, title, url)

class LicensesView: VerticalView("Licenses") {
    private var licensesListState = mutableStateListOf<LicenseState>()
    var licenses: List<License>
        get() = licensesListState.map { it.toLicense() }
        set(value) {
            licensesListState.clear()
            licensesListState.addAll(value.map { it.toLicenseState() })
        }
    private val availableLicensesState = mutableStateListOf<License>()
    private val licensesOffersToShow = mutableStateListOf<License>()
    private var licenseSearchFilter by mutableStateOf("")

    init {
        CoroutineScope(Dispatchers.Default).launch {
            val client = HttpClient()
            availableLicensesState.addAll(client.getLicenses().values)
            client.close()
        }
    }

    override val content: @Composable ColumnScope.() -> Unit = {
        CommonTextField(licenseSearchFilter, "Search filter") { filterText ->
            licenseSearchFilter = filterText
            licensesOffersToShow.clear()
            if (licenseSearchFilter.isNotEmpty()) {
                licensesOffersToShow.addAll(
                    availableLicensesState.filter { filterText.all { symbol -> symbol.toLowerCase() in it.title } }
                )
            }
        }
        Column {
            licensesOffersToShow.forEach {
                Column(Modifier.padding(16.dp, 8.dp, 8.dp, 8.dp)) {
                    CommonText(it.title, Modifier.clickable {
                        licensesListState.add(it.toLicenseState())
                        licenseSearchFilter = ""
                        licensesOffersToShow.clear()
                    })
                    Divider()
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
                    CommonText("Remove")
                }
            }
        }
    }
}
