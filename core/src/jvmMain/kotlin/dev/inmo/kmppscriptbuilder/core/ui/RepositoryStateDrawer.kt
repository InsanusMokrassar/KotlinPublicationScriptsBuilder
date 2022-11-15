package dev.inmo.kmppscriptbuilder.core.ui

import dev.inmo.kmppscriptbuilder.core.ui.utils.CommonTextField
import dev.inmo.kmppscriptbuilder.core.ui.utils.Drawer

actual object RepositoryStateDrawer : Drawer<RepositoriesView> {
    override fun RepositoriesView.draw() {
        CommonTextField(
            item.name,
            "Repository name"
        ) { item.name = it }
        CommonTextField(
            item.url,
            "Repository url"
        ) { item.url = it }
    }

}
