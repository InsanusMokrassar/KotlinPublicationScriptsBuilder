package dev.inmo.kmppscriptbuilder.core.utils

import dev.inmo.kmppscriptbuilder.core.models.Config
import kotlinx.browser.document
import kotlinx.dom.appendElement
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.url.URL
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag
import org.w3c.files.FileReader
import org.w3c.files.get

fun saveFile(content: String, filename: String) {
    val a = document.body!!.appendElement("a") {
        setAttribute("style", "visibility:hidden; display: none")
    } as HTMLAnchorElement
    val blob = Blob(arrayOf(content), BlobPropertyBag(
        "application/*;charset=utf-8"
    ))
    val url = URL.createObjectURL(blob)
    a.href = url
    a.download = filename
    a.click()
    URL.revokeObjectURL(url)
    a.remove()
}

actual fun openNewConfig(onParsed: (Config) -> Unit) {
    val targetInput = document.body!!.appendElement("input") {
        setAttribute("style", "visibility:hidden; display: none")
    } as HTMLInputElement
    targetInput.type = "file"
    targetInput.onchange = {
        targetInput.files ?.also { files ->
            for (i in (0 until files.length) ) {
                files[i] ?.also { file ->
                    val reader = FileReader()

                    reader.onload = {
                        val content = it.target.asDynamic().result as String
                        onParsed(serialFormat.decodeFromString(Config.serializer(), content))
                        false
                    }

                    reader.readAsText(file)
                }
            }
        }
    }
    targetInput.click()
    targetInput.remove()
}

actual fun saveConfig(config: Config): Boolean {
    saveFile(
        serialFormat.encodeToString(Config.serializer(), config),
        "publish.kpsb"
    )
    return true
}

actual fun exportGradle(config: Config): Boolean {
    val filename = "publish.gradle"

    val content = config.run {
        type.buildMavenGradleConfig(
            mavenConfig,
            licenses
        )
    }

    saveFile(content, filename)
    return true
}

actual fun saveAs(config: Config): Boolean {
    saveFile(
        serialFormat.encodeToString(Config.serializer(), config),
        "publish.kpsb"
    )
    return true
}
