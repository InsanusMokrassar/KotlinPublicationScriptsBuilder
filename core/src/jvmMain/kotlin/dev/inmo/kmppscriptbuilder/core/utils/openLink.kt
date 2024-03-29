package dev.inmo.kmppscriptbuilder.core.utils

import java.awt.Desktop
import java.net.URI

actual fun openLink(link: String): Boolean {
    val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        try {
            desktop.browse(URI(link))
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return false
}
