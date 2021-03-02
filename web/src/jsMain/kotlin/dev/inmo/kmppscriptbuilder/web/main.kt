package dev.inmo.kmppscriptbuilder.web

import dev.inmo.kmppscriptbuilder.core.models.Config
import dev.inmo.kmppscriptbuilder.core.utils.serialFormat
import dev.inmo.kmppscriptbuilder.web.views.*
import kotlinx.browser.document
import kotlinx.dom.appendElement
import org.w3c.dom.*
import org.w3c.dom.url.URL
import org.w3c.files.*

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

fun main() {
    document.addEventListener(
        "DOMContentLoaded",
        {
            val builderView = BuilderView()

            (document.getElementById("openConfig") as HTMLElement).onclick = {
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
                                    builderView.config = serialFormat.decodeFromString(Config.serializer(), content)
                                    false
                                }

                                reader.readAsText(file)
                            }
                        }
                    }
                }
                targetInput.click()
                targetInput.remove()
                false
            }
            (document.getElementById("saveConfig") as HTMLElement).onclick = {
                val filename = "publish.kpsb"
                val content = serialFormat.encodeToString(Config.serializer(), builderView.config)

                saveFile(content, filename)

                false
            }
            (document.getElementById("exportScript") as HTMLElement).onclick = {
                val filename = "publish.gradle"

                val content = builderView.config.run {
                    type.buildMavenGradleConfig(
                        mavenConfig,
                        licenses
                    )
                }

                saveFile(content, filename)
                false
            }
        }
    )
}
