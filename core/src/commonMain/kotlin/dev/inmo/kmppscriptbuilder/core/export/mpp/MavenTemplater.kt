package dev.inmo.kmppscriptbuilder.core.export.mpp

import dev.inmo.kmppscriptbuilder.core.export.generateMavenConfig
import dev.inmo.kmppscriptbuilder.core.models.*

fun MavenConfig.buildMultiplatformMavenConfig(licenses: List<License>): String = """
apply plugin: 'maven-publish'

task javadocsJar(type: Jar) {
    archiveClassifier = 'javadoc'
}

publishing {
    publications.all {
        artifact javadocsJar

        pom {
            description = "$description"
            name = "$name"
            url = "$url"

            scm {
                developerConnection = "scm:git:[fetch=]${vcsUrl}[push=]${vcsUrl}"
                url = "$vcsUrl"
            }

            developers {
                ${developers.joinToString("\n") { """
                    developer {
                        id = "${it.id}"
                        name = "${it.name}"
                        email = "${it.eMail}"
                    }
                """ }}
            }

            licenses {
                ${licenses.joinToString("\n") { """
                    license {
                        name = "${it.title}"
                        url = "${it.url}"
                    }
                """ }}
            }
        }
        repositories {
            ${repositories.joinToString("\n            ") { it.build("            ") }}
        }
    }
}
    ${gpgSigning.generateMavenConfig()}
""".trimIndent()
