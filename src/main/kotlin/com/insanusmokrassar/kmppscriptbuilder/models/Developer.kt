package com.insanusmokrassar.kmppscriptbuilder.models

import kotlinx.serialization.Serializable

@Serializable
data class Developer(
    val id: String,
    val name: String,
    val eMail: String
)
