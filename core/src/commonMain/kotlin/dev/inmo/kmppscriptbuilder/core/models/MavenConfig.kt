package dev.inmo.kmppscriptbuilder.core.models

import kotlinx.serialization.Serializable

const val defaultProjectName = "\${project.name}"
const val defaultProjectDescription = "\${project.name}"

@Serializable
sealed class GpgSigning(val name: String) {
    @Serializable
    object Disabled : GpgSigning("Disabled")
    @Serializable
    object Optional : GpgSigning("Optional")
    @Serializable
    object Enabled : GpgSigning("Enabled")
}

@Serializable
data class MavenConfig(
    val name: String,
    val description: String,
    val url: String,
    val vcsUrl: String,
    val developers: List<Developer>,
    val repositories: List<MavenPublishingRepository> = emptyList(),
    val gpgSigning: GpgSigning = GpgSigning.Disabled,
    @Deprecated("Replaced with gpgSigning")
    val includeGpgSigning: Boolean = false,
)

@Serializable
data class MavenPublishingRepository(
    val name: String,
    val url: String,
    val credsType: CredentialsType = CredentialsType.UsernameAndPassword(
        "${name.uppercase()}_USER",
        "${name.uppercase()}_PASSWORD"
    )
) {
    val defaultUsernameProperty = "${name.uppercase()}_USER"
    val defaultPasswordProperty = "${name.uppercase()}_PASSWORD"
    val defaultHeaderValueProperty = "${name.uppercase()}_TOKEN"

    @Serializable
    sealed interface CredentialsType {
        @Serializable
        object Nothing: CredentialsType {
            override fun buildCheckPart(): String = "true"
            override fun buildCredsPart(): String = ""
        }
        @Serializable
        data class UsernameAndPassword(
            val usernameProperty: String,
            val passwordProperty: String
        ): CredentialsType {
            override fun buildCheckPart(): String = "(project.hasProperty('${usernameProperty}') || System.getenv('${usernameProperty}') != null) && (project.hasProperty('${passwordProperty}') || System.getenv('${passwordProperty}') != null)"
            override fun buildCredsPart(): String {
return """
        credentials {
            username = project.hasProperty('${usernameProperty}') ? project.property('${usernameProperty}') : System.getenv('${usernameProperty}')
            password = project.hasProperty('${passwordProperty}') ? project.property('${passwordProperty}') : System.getenv('${passwordProperty}')
        }
"""
            }
        }
        @Serializable
        data class HttpHeaderCredentials(
            val headerName: String,
            val headerValueProperty: String
        ): CredentialsType {
            override fun buildCheckPart(): String = "project.hasProperty('${headerValueProperty}') || System.getenv('${headerValueProperty}') != null"
            override fun buildCredsPart(): String {
return """
        credentials(HttpHeaderCredentials) {
            name = "$headerName"
            value = project.hasProperty('${headerValueProperty}') ? project.property('${headerValueProperty}') : System.getenv('${headerValueProperty}')
        }
"""
            }
        }

        fun buildCheckPart(): String
        fun buildCredsPart(): String
    }
    private val nameCapitalized by lazy {
        name.uppercase()
    }

    fun build(indent: String): String {
        return """if (${credsType.buildCheckPart()}) {
    maven {
        name = "$name"
        url = uri("$url")
${credsType.buildCredsPart()}
    }
}""".replace("\n", "\n$indent")
    }
}

val SonatypeRepository = MavenPublishingRepository("sonatype", "https://oss.sonatype.org/service/local/staging/deploy/maven2/")
