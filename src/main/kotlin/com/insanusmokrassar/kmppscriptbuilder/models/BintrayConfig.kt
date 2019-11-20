package com.insanusmokrassar.kmppscriptbuilder.models

import kotlinx.serialization.Serializable

@Serializable
data class BintrayConfig(
    val repoUser: String = "\${project.hasProperty('BINTRAY_USER') ? project.property('BINTRAY_USER') : System.getenv('BINTRAY_USER')}",
    val repo: String,
    val packageName: String,
    val packageVcs: String,
    val autoPublish: Boolean = false,
    val overridePublish: Boolean = false
)
