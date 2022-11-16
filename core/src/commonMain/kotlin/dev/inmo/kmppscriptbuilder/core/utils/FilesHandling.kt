package dev.inmo.kmppscriptbuilder.core.utils

import dev.inmo.kmppscriptbuilder.core.models.Config
import dev.inmo.micro_utils.common.MPPFile

internal const val appExtension = "kpsb"

expect fun openNewConfig(onParsed: (Config) -> Unit)

expect fun saveConfig(config: Config): Boolean

expect fun exportGradle(config: Config): Boolean

expect fun saveAs(config: Config): Boolean
