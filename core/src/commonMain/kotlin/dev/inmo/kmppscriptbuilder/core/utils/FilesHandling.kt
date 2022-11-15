package dev.inmo.kmppscriptbuilder.core.utils

import dev.inmo.kmppscriptbuilder.core.models.Config
import dev.inmo.micro_utils.common.MPPFile

internal const val appExtension = "kpsb"

private var lastFile: MPPFile? = null

fun loadConfigFile(file: MPPFile): Config {
    lastFile = file
    return serialFormat.decodeFromString(Config.serializer(), file.text())
}

expect fun MPPFile.text(): String

expect fun loadConfig(): Config?

expect fun saveConfig(config: Config): Boolean

expect fun exportGradle(config: Config): Boolean

expect fun saveAs(config: Config): Boolean
