package dev.inmo.kmppscriptbuilder.core.models

import kotlinx.serialization.Serializable

@Serializable
data class MavenConfig(
    val name: String,
    val description: String,
    val url: String,
    val vcsUrl: String,
    val includeGpgSigning: Boolean = false,
    val developers: List<Developer>,
    val repositories: List<MavenPublishingRepository> = emptyList()
)

@Serializable
data class MavenPublishingRepository(
    val name: String,
    val url: String
) {
    private val nameCapitalized by lazy {
        name.toUpperCase()
    }

    fun build(indent: String) = """maven {
    name = "$name"
    url = uri("$url")
    credentials {
        username = project.hasProperty('${nameCapitalized}_USER') ? project.property('${nameCapitalized}_USER') : System.getenv('${nameCapitalized}_USER')
        password = project.hasProperty('${nameCapitalized}_PASSWORD') ? project.property('${nameCapitalized}_PASSWORD') : System.getenv('${nameCapitalized}_PASSWORD')
    }
}""".replace("\n", "\n$indent")
}

val SonatypeRepository = MavenPublishingRepository("sonatype", "https://oss.sonatype.org/service/local/staging/deploy/maven2/")
