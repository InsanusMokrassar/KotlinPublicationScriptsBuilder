package dev.inmo.kmppscriptbuilder.web

import dev.inmo.kmppscriptbuilder.web.views.*
import kotlinx.browser.document

fun main() {
    document.addEventListener(
        "DOMContentLoaded",
        {
            val builderView = BuilderView()
            document.body ?.onclick = {
                println(builderView.config)
                Unit
            }
        }
    )
}
