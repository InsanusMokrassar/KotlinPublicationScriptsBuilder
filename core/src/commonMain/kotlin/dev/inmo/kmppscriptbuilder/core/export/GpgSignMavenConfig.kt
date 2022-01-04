package dev.inmo.kmppscriptbuilder.core.export

import dev.inmo.kmppscriptbuilder.core.models.GpgSigning

fun GpgSigning.generateMavenConfig() = when (this) {
    GpgSigning.Disabled -> ""
    GpgSigning.Optional ->
"""
if (project.hasProperty("signing.gnupg.keyName")) {
    apply plugin: 'signing'
    
    signing {
        useGpgCmd()
    
        sign publishing.publications
    }
    
    task signAll {
        tasks.withType(Sign).forEach {
            dependsOn(it)
        }
    }
}
"""
    GpgSigning.Enabled ->
"""
apply plugin: 'signing'

signing {
    useGpgCmd()

    sign publishing.publications
}

task signAll {
    tasks.withType(Sign).forEach {
        dependsOn(it)
    }
}
"""
}
