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

    // Workaround to make android sign operations depend on signing tasks
    project.getTasks().withType(AbstractPublishToMaven.class).configureEach {
        def signingTasks = project.getTasks().withType(Sign.class)
        mustRunAfter(signingTasks)
    }
    // Workaround to make test tasks use sign
    project.getTasks().withType(Sign.class).configureEach { signTask ->
        def withoutSign = (signTask.name.startsWith("sign") ? signTask.name.minus("sign") : signTask.name)
        def pubName = withoutSign.endsWith("Publication") ? withoutSign.substring(0, withoutSign.length() - "Publication".length()) : withoutSign
        // These tasks only exist for native targets, hence findByName() to avoid trying to find them for other targets

        // Task ':linkDebugTest<platform>' uses this output of task ':sign<platform>Publication' without declaring an explicit or implicit dependency
        def debugTestTask = tasks.findByName("linkDebugTest${'$'}pubName")
        if (debugTestTask != null) {
            signTask.mustRunAfter(debugTestTask)
        }
        // Task ':compileTestKotlin<platform>' uses this output of task ':sign<platform>Publication' without declaring an explicit or implicit dependency
        def testTask = tasks.findByName("compileTestKotlin${'$'}pubName")
        if (testTask != null) {
            signTask.mustRunAfter(testTask)
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

// Workaround to make android sign operations depend on signing tasks
project.getTasks().withType(AbstractPublishToMaven.class).configureEach {
    def signingTasks = project.getTasks().withType(Sign.class)
    mustRunAfter(signingTasks)
}
// Workaround to make test tasks use sign
project.getTasks().withType(Sign.class).configureEach { signTask ->
    def withoutSign = (signTask.name.startsWith("sign") ? signTask.name.minus("sign") : signTask.name)
    def pubName = withoutSign.endsWith("Publication") ? withoutSign.substring(0, withoutSign.length() - "Publication".length()) : withoutSign
    // These tasks only exist for native targets, hence findByName() to avoid trying to find them for other targets

    // Task ':linkDebugTest<platform>' uses this output of task ':sign<platform>Publication' without declaring an explicit or implicit dependency
    def debugTestTask = tasks.findByName("linkDebugTest${'$'}pubName")
    if (debugTestTask != null) {
        signTask.mustRunAfter(debugTestTask)
    }
    // Task ':compileTestKotlin<platform>' uses this output of task ':sign<platform>Publication' without declaring an explicit or implicit dependency
    def testTask = tasks.findByName("compileTestKotlin${'$'}pubName")
    if (testTask != null) {
        signTask.mustRunAfter(testTask)
    }
}
"""
}
