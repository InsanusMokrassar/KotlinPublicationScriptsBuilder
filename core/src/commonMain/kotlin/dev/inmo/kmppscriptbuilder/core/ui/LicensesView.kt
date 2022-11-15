package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.inmo.kmppscriptbuilder.core.models.License
import dev.inmo.kmppscriptbuilder.core.models.getLicenses
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class LicenseState(
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

class LicensesView: VerticalView("Licenses") {
    internal var licensesListState = mutableStateListOf<LicenseState>()
    var licenses: List<License>
        get() = licensesListState.map { it.toLicense() }
        set(value) {
            licensesListState.clear()
            licensesListState.addAll(value.map { it.toLicenseState() })
        }
    internal val availableLicensesState = mutableStateListOf<License>()
    internal val licensesOffersToShow = mutableStateListOf<License>()
    internal var licenseSearchFilter by mutableStateOf("")

    init {
        CoroutineScope(Dispatchers.Default).launch {
            val client = HttpClient()
            availableLicensesState.addAll(client.getLicenses().values)
            client.close()
        }
    }

    override val content: @Composable () -> Unit = {
        with(LicensesDrawer) { draw() }
    }
}
