package dev.inmo.kmppscriptbuilder.core

import dev.inmo.kmppscriptbuilder.core.ui.BuilderView
import kotlinx.browser.window
import org.jetbrains.compose.web.renderComposableInBody

fun main() {
    window.addEventListener("load", {
        val builder = BuilderView()
        renderComposableInBody {
            builder.build()
        }
    })
}
