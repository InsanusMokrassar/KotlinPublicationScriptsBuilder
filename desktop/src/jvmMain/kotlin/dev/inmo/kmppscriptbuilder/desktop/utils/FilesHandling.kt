package dev.inmo.kmppscriptbuilder.desktop.utils

import dev.inmo.kmppscriptbuilder.core.models.Config
import dev.inmo.kmppscriptbuilder.core.utils.serialFormat
import java.io.File
import javax.swing.JFileChooser

private const val appExtension = "kpsb"

private var lastFile: File? = null

fun loadConfigFile(file: File): Config {
    lastFile = file
    return serialFormat.decodeFromString(Config.serializer(), file.readText())
}

fun loadConfig(): Config? {
    val fc = JFileChooser(lastFile ?.parent)
    fc.addChoosableFileFilter(FileFilter("Kotlin Publication Scripts Builder", Regex(".*\\.$appExtension")))
    fc.addChoosableFileFilter(FileFilter("JSON", Regex(".*\\.json")))
    return when (fc.showOpenDialog(null)) {
        JFileChooser.APPROVE_OPTION -> {
            val file = fc.selectedFile
            lastFile = file
            return serialFormat.decodeFromString(Config.serializer(), fc.selectedFile.readText())
        }
        else -> null
    }
}

fun saveConfig(config: Config): Boolean {
    return lastFile ?.also {
        it.writeText(serialFormat.encodeToString(Config.serializer(), config))
    } != null
}

fun exportGradle(config: Config): Boolean {
    val fc = JFileChooser(lastFile ?.parent)
    fc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    return when (fc.showSaveDialog(null)) {
        JFileChooser.APPROVE_OPTION -> {
            val file = fc.selectedFile
            val mavenConfigContent = config.type.buildMavenGradleConfig(
                config.mavenConfig,
                config.licenses
            )
            File(file, "publish.gradle").apply {
                delete()
                createNewFile()
                writeText(mavenConfigContent)
            }
            true
        }
        else -> false
    }
}

fun saveAs(config: Config): Boolean {
    val fc = JFileChooser(lastFile ?.parent)
    fc.addChoosableFileFilter(FileFilter("Kotlin Publication Scripts Builder", Regex(".*\\.$appExtension")))
    fc.addChoosableFileFilter(FileFilter("JSON", Regex(".*\\.json")))
    return when (fc.showSaveDialog(null)) {
        JFileChooser.APPROVE_OPTION -> {
            val file = fc.selectedFile
            val resultFile = if (file.extension == "") {
                File(file.parentFile, "${file.name}.$appExtension")
            } else {
                file
            }
            resultFile.writeText(serialFormat.encodeToString(Config.serializer(), config))
            lastFile = resultFile
            true
        }
        else -> false
    }
}
