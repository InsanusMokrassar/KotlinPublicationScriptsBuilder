package dev.inmo.kmppscriptbuilder.web.views

import dev.inmo.kmppscriptbuilder.core.models.*
import dev.inmo.kmppscriptbuilder.web.utils.ukActive
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

class ProjectTypeView : View {
    private val mppProjectTypeElement = document.getElementById("mppProjectType") as HTMLElement
    private val jvmProjectTypeElement = document.getElementById("jvmProjectType") as HTMLElement
    private val jsProjectTypeElement = document.getElementById("jsProjectType") as HTMLElement

    var projectType: ProjectType
        get() = when {
            jvmProjectTypeElement.ukActive -> JVMProjectType
            jsProjectTypeElement.ukActive -> JSProjectType
            else -> MultiplatformProjectType
        }
        set(value) {
            mppProjectTypeElement.ukActive = value == MultiplatformProjectType
            jvmProjectTypeElement.ukActive = value == JVMProjectType
            jsProjectTypeElement.ukActive = value == JSProjectType
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
        jsProjectTypeElement.onclick = {
            projectType = JSProjectType
            Unit
        }
    }
}
