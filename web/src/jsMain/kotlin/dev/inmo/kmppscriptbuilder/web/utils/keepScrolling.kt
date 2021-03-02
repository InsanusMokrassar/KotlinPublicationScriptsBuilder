package dev.inmo.kmppscriptbuilder.web.utils

import kotlinx.browser.document

inline fun <R> keepScrolling(crossinline block: () -> R): R = document.body ?.let {
    val (x, y) = (it.scrollLeft to it.scrollTop)
    return block().also { _ ->
        it.scrollTo(x, y)
    }
} ?: block()
