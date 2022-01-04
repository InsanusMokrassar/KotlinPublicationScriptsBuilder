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
    val url: String
) {
    private val nameCapitalized by lazy {
        name.toUpperCase()
    }

    fun build(indent: String): String {
        val usernameProperty = "${nameCapitalized}_USER"
        val passwordProperty = "${nameCapitalized}_PASSWORD"
        return """if ((project.hasProperty('${usernameProperty}') || System.getenv('${usernameProperty}') != null) && (project.hasProperty('${passwordProperty}') || System.getenv('${passwordProperty}') != null)) {
    maven {
        name = "$name"
        url = uri("$url")
        credentials {
            username = project.hasProperty('${usernameProperty}') ? project.property('${usernameProperty}') : System.getenv('${usernameProperty}')
            password = project.hasProperty('${passwordProperty}') ? project.property('${passwordProperty}') : System.getenv('${passwordProperty}')
        }
    }
}""".replace("\n", "\n$indent")
    }
}

val SonatypeRepository = MavenPublishingRepository("sonatype", "https://oss.sonatype.org/service/local/staging/deploy/maven2/")
