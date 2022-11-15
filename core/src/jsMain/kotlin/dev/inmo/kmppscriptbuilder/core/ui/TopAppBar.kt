package dev.inmo.kmppscriptbuilder.core.ui

import androidx.compose.runtime.Composable
import dev.inmo.jsuikit.elements.Icon
import dev.inmo.jsuikit.elements.NavItemElement
import dev.inmo.jsuikit.elements.Navbar
import dev.inmo.jsuikit.elements.NavbarNav
import dev.inmo.jsuikit.elements.drawAsLink
import dev.inmo.jsuikit.modifiers.UIKitMargin
import dev.inmo.jsuikit.modifiers.UIKitPadding
import dev.inmo.jsuikit.modifiers.UIKitText
import dev.inmo.jsuikit.modifiers.UIKitTooltipModifier
import dev.inmo.jsuikit.modifiers.builder
import dev.inmo.jsuikit.modifiers.include
import dev.inmo.jsuikit.utils.AttrsWithContentBuilder
import dev.inmo.kmppscriptbuilder.core.models.Config
import dev.inmo.kmppscriptbuilder.core.utils.exportGradle
import dev.inmo.kmppscriptbuilder.core.utils.openNewConfig
import dev.inmo.kmppscriptbuilder.core.utils.saveConfig
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Composable
actual fun TopAppBar(
    config: Config,
    saveAvailable: Boolean,
    onSaveAvailable: (Boolean) -> Unit,
    onNewConfig: (Config) -> Unit
) {
    Navbar(
        leftBuilder = AttrsWithContentBuilder {
            Div(
                {
                    onClick {
                        console.log(config)
                    }
                    include(UIKitPadding.Size.Small, UIKitText.Style.Lead)
                }
            ) {
                Text("Kotlin publication scripts builder")
            }
            Div(UIKitMargin.Small.builder()) {
                A("https://github.com/InsanusMokrassar/KotlinPublicationScriptsBuilder") {
                    Img("https://img.shields.io/github/stars/InsanusMokrassar/KotlinPublicationScriptsBuilder?label=Github&style=plastic")
                }
            }
        },
        rightBuilder = AttrsWithContentBuilder {
            NavbarNav(
                AttrsWithContentBuilder {
                    NavItemElement(
                        UIKitTooltipModifier("Open file")
                    ) {
                        Icon.Storage.Pull.drawAsLink {
                            openNewConfig(onNewConfig)
                        }
                    }
                },
                AttrsWithContentBuilder {
                    NavItemElement(
                        UIKitTooltipModifier("Save config")
                    ) {
                        Icon.Storage.Push.drawAsLink {
                            saveConfig(config)
                        }
                    }
                },
                AttrsWithContentBuilder {
                    NavItemElement(
                        UIKitTooltipModifier("Export gradle script")
                    ) {
                        Icon.Storage.Upload.drawAsLink {
                            exportGradle(config)
                        }
                    }
                },
            )
        }
    )
}
