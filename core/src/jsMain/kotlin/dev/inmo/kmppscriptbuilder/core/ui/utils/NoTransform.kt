package dev.inmo.kmppscriptbuilder.core.ui.utils

import dev.inmo.jsuikit.modifiers.UIKitCustom
import dev.inmo.jsuikit.modifiers.UIKitUtility

val ClassNoTransform = UIKitCustom(arrayOf("no-transform"))

val UIKitUtility.Companion.NoTransform
    get() = ClassNoTransform
