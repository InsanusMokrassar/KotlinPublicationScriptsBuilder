package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.License
import dev.inmo.kmppscriptbuilder.core.models.getLicenses
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonText
import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.DefaultSmallVerticalMargin
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LicenseState(
    id: String = "",
    title: String = "",
    url: String? = null
) {
    var id: String by mutableStateOf(id)
    var title: String by mutableStateOf(title)
    var url: String? by mutableStateOf(url)

    fun toLicense() = License(id, title, url)
}

internal fun License.toLicenseState() = LicenseState(id, title, url)

expect object LicensesDrawer : Drawer<LicensesView>

class LicensesView : ListView<LicenseState>("Licenses") {
    var licenses: List<License>
        get() = itemsList.map { it.toLicense() }
        set(value) {
            itemsList.clear()
            itemsList.addAll(value.map { it.toLicenseState() })
        }
    internal val availableLicensesState = mutableStateListOf<License>()
    internal var licenseSearchFilter by mutableStateOf("")
    internal val searchFieldFocused = mutableStateOf(false)
    internal val licensesOffersToShow = derivedStateOf {
        val query = licenseSearchFilter.lowercase()
        availableLicensesState.filter {
            it.title.lowercase().contains(query)
        }
    }

    override val addItemText: String
        get() = "Add empty license"

    init {
        CoroutineScope(Dispatchers.Default).launch {
            val client = HttpClient()
            availableLicensesState.addAll(client.getLicenses().values)
            client.close()
        }
    }

    override fun createItem(): LicenseState = LicenseState()

    @Composable
    override fun buildView(item: LicenseState) {
        CommonText("License ID")
        CommonTextField(
            item.id,
            "Short name like \"Apache-2.0\"",
        ) { item.id = it }
        CommonText("License title")
        CommonTextField(
            item.title,
            "Official title of license (like \"Apache Software License 2.0\")",
        ) { item.title = it }
        CommonText("License URL")
        CommonTextField(
            item.url ?: "",
            "Link to your LICENSE file OR official license file (like \"https://opensource.org/licenses/Apache-2.0\")",
        ) { item.url = it }
    }

    override val content: @Composable () -> Unit = {
        CommonTextField(
            licenseSearchFilter,
            "Search filter",
            onFocusChanged = {
                searchFieldFocused.value = it
            }
        ) { filterText ->
            licenseSearchFilter = filterText
        }
        DefaultSmallVerticalMargin()

        with(LicensesDrawer) { draw() }

        DefaultSmallVerticalMargin()

        super.content()
    }
}
