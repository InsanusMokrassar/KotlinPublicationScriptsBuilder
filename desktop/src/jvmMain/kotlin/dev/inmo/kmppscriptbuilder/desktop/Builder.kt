package dev.inmo.kmppscriptbuilder.desktop

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.inmo.kmppscriptbuilder.desktop.utils.init
import dev.inmo.kmppscriptbuilder.desktop.utils.loadConfigFile
import dev.inmo.kmppscriptbuilder.desktop.views.BuilderView
import java.io.File

//private val uncaughtExceptionsBC = BroadcastChannel<DefaultErrorHandler.ErrorEvent>(Channel.CONFLATED)
//val uncaughtExceptionsFlow: Flow<DefaultErrorHandler.ErrorEvent> = uncaughtExceptionsBC.asFlow()

fun main(args: Array<String>) = application {
    Window(onCloseRequest = ::exitApplication, title = "Kotlin Multiplatform Publishing Builder") {
        val builder = BuilderView()
        MaterialTheme(
            Colors(
                primary = Color(0x01, 0x57, 0x9b),
                primaryVariant = Color(0x00, 0x2f, 0x6c),
                secondary = Color(0xb2, 0xeb, 0xf2),
                secondaryVariant = Color(0x81, 0xb9, 0xbf),
                background = Color(0xe1, 0xe2, 0xe1),
                surface = Color(0xf5, 0xf5, 0xf6),
                error = Color(0xb7, 0x1c, 0x1c),
                onPrimary = Color.White,
                onSecondary = Color.Black,
                onBackground = Color.Black,
                onSurface = Color.Black,
                onError = Color.White,
                isLight = MaterialTheme.colors.isLight,
            )
        ) {
            Box(
                Modifier.fillMaxSize()
                    .background(color = Color(245, 245, 245))
            ) {

                val stateVertical = rememberScrollState(0)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(stateVertical)
                ) {
                    builder.init()

                }

                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(stateVertical)
                )
            }
        }

        if (args.isNotEmpty()) {
            val config = loadConfigFile(File(args.first()))
            builder.config = config
        }
    }
}
