package dev.inmo.kmppscriptbuilder.core.utils

actual fun openLink(link: String): Boolean {
    dev.inmo.micro_utils.common.openLink(link)
    return true
}
