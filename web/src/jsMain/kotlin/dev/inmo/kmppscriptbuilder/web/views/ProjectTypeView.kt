package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.kmppscriptbuilder.core.models.*
import dev.inmo.kmppscriptbuilder.web.utils.ukActive
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

class ProjectTypeView : View {
    private val mppProjectTypeElement = document.getElementById("mppProjectType") as HTMLElement
    private val jvmProjectTypeElement = document.getElementById("jvmProjectType") as HTMLElement

    var projectType: ProjectType
        get() = if (jvmProjectTypeElement.ukActive) {
            JVMProjectType
        } else {
            MultiplatformProjectType
        }
        set(value) {
            mppProjectTypeElement.ukActive = value == MultiplatformProjectType
            jvmProjectTypeElement.ukActive = value == JVMProjectType
        }

    init {
        mppProjectTypeElement.onclick = {
            projectType = MultiplatformProjectType
            Unit
        }
        jvmProjectTypeElement.onclick = {
            projectType = JVMProjectType
            Unit
        }
    }
}