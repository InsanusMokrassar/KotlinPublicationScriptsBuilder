package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.kmppscriptbuilder.core.models.Config
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

class BuilderView : View {
    private val projectTypeView = ProjectTypeView()
    private val licensesView = LicensesView(document.getElementById("licensesListDiv") as HTMLElement)
    private val mavenInfoTypeView = MavenProjectInfoView()

    var config: Config
        get() = Config(
            licensesView.licenses,
            mavenInfoTypeView.mavenConfig,
            projectTypeView.projectType
        )
        set(value) {
            licensesView.licenses = value.licenses
            mavenInfoTypeView.mavenConfig = value.mavenConfig
            projectTypeView.projectType = value.type
        }
}