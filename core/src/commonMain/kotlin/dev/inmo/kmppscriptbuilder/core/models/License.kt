package dev.inmo.kmppscriptbuilder.core.models

import dev.inmo.kmppscriptbuilder.core.utils.serialFormat
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

@Serializable
data class License(
    val id: String,
    val title: String,
    val url: String? = null
)

private val commonLicensesListDeserializer = MapSerializer(String.serializer(), License.serializer())

private var licenses: Map<String, License>? = null

suspend fun HttpClient.getLicenses(): Map<String, License> {
    val answer = get<String> {
        url("https://licenses.opendefinition.org/licenses/groups/all.json")
    }
    return serialFormat.decodeFromString(commonLicensesListDeserializer, answer).also { gotLicenses ->
        licenses = gotLicenses
    }
}

suspend fun HttpClient.searchLicense(name: String): List<License> {
    val licenses = licenses ?: getLicenses()
    val lowerCase = name.lowercase()
    val upperCase = name.uppercase()
    return licenses.values.filter {
        it.title.lowercase().contains(lowerCase) || it.title.uppercase().contains(upperCase) || it.title.contains(name)
            || it.id.lowercase().contains(lowerCase) || it.id.uppercase().contains(upperCase) || it.id.contains(name)
    }
}

fun Map<String, License>.searchLicense(name: String): List<License> {
    val lowerCase = name.lowercase()
    val upperCase = name.uppercase()
    return values.filter {
        it.title.lowercase().contains(lowerCase) || it.title.uppercase().contains(upperCase) || it.title.contains(name)
            || it.id.lowercase().contains(lowerCase) || it.id.uppercase().contains(upperCase) || it.id.contains(name)
    }
}
