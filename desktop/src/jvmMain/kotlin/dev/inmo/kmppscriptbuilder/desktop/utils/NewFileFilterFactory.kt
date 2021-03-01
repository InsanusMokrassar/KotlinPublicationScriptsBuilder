package dev.inmo.kmppscriptbuilder.desktop.utils

import java.io.File
import javax.swing.filechooser.FileFilter

fun FileFilter(description: String, fileFilter: (File) -> Boolean) = object : FileFilter() {
    override fun accept(f: File?): Boolean {
        return fileFilter(f ?: return false)
    }

    override fun getDescription(): String = description
}

fun FileFilter(description: String, nameRegex: Regex) = FileFilter(description) {
    it.name.matches(nameRegex)
}
