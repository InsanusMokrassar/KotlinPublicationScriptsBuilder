package dev.inmo.kmppscriptbuilder.web

import dev.inmo.kmppscriptbuilder.web.views.MavenProjectInfoView
import dev.inmo.kmppscriptbuilder.web.views.ProjectTypeView
import kotlinx.browser.document

fun main() {
    document.addEventListener(
        "DOMContentLoaded",
        {
            val projectTypeView = ProjectTypeView()
            val mavenInfoTypeView = MavenProjectInfoView()
        }
    )
}
