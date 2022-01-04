package dev.inmo.kmppscriptbuilder.core.models

import dev.inmo.kmppscriptbuilder.core.export.js_only.buildJsOnlyMavenConfig
import dev.inmo.kmppscriptbuilder.core.export.jvm_only.buildJvmOnlyMavenConfig
import dev.inmo.kmppscriptbuilder.core.export.mpp.buildMultiplatformMavenConfig
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(ProjectTypeSerializer::class)
sealed class ProjectType {
    abstract val name: String
//    abstract fun buildBintrayGradleConfig(bintrayConfig: BintrayConfig, licenses: List<License>): String
    abstract fun buildMavenGradleConfig(mavenConfig: MavenConfig, licenses: List<License>): String
}

@Serializer(ProjectType::class)
object ProjectTypeSerializer : KSerializer<ProjectType> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor
    override fun deserialize(decoder: Decoder): ProjectType {
        return when (decoder.decodeString()) {
            JVMProjectType.name -> JVMProjectType
            else -> MultiplatformProjectType
        }
    }

    override fun serialize(encoder: Encoder, value: ProjectType) {
        encoder.encodeString(value.name)
    }
}

object MultiplatformProjectType : ProjectType() {
    override val name: String = "Multiplatform"

    override fun buildMavenGradleConfig(
        mavenConfig: MavenConfig,
        licenses: List<License>
    ): String = mavenConfig.buildMultiplatformMavenConfig(
        licenses
    )
}

object JVMProjectType : ProjectType() {
    override val name: String = "JVM"

    override fun buildMavenGradleConfig(
        mavenConfig: MavenConfig,
        licenses: List<License>
    ): String = mavenConfig.buildJvmOnlyMavenConfig(
        licenses
    )
}

object JSProjectType : ProjectType() {
    override val name: String = "JS"

    override fun buildMavenGradleConfig(
        mavenConfig: MavenConfig,
        licenses: List<License>
    ): String = mavenConfig.buildJsOnlyMavenConfig(licenses)
}

@Serializable
data class Config(
    val licenses: List<License>,
    val mavenConfig: MavenConfig,
    val type: ProjectType = MultiplatformProjectType
)
