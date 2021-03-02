package dev.inmo.kmppscriptbuilder.web.utils

import org.w3c.dom.HTMLElement

var HTMLElement.ukActive: Boolean
    get() = classList.contains("uk-active")
    set(value) {
        if (value) {
            classList.add("uk-active")
        } else {
            classList.remove("uk-active")
        }
    }
