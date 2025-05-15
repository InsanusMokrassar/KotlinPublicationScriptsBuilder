package dev.inmo.kmppscriptbuilder.core.export.jvm_only

import dev.inmo.kmppscriptbuilder.core.export.generateCentralSonatypeUploadingPart
import dev.inmo.kmppscriptbuilder.core.export.generateCentralSonatypeUploadingPartImports
import dev.inmo.kmppscriptbuilder.core.export.generateMavenConfig
import dev.inmo.kmppscriptbuilder.core.models.*

fun MavenConfig.buildJvmOnlyMavenConfig(licenses: List<License>): String = """
${if (includeCentralSonatypeUploadingScript) "$generateCentralSonatypeUploadingPartImports\n" else ""}
apply plugin: 'maven-publish'
${if (includeCentralSonatypeUploadingScript) "$generateCentralSonatypeUploadingPart\n" else ""}

task javadocJar(type: Jar) {
    from javadoc
    archiveClassifier = 'javadoc'
}
task sourcesJar(type: Jar) {
    from sourceSets.main.allSource
    archiveClassifier = 'sources'
}

publishing {
    publications {
        maven(MavenPublication) {
            from components.java

            artifact javadocJar
            artifact sourcesJar

            pom {
                resolveStrategy = Closure.DELEGATE_FIRST

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
                ${repositories.joinToString("\n                ") { it.build("                ") }}
            }
        }
    }
}
${gpgSigning.generateMavenConfig()}
""".trimIndent()
