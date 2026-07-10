package com.moim.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Transparent = Color(0x00000000)
val Black = Color(0xFF222222)
val White = Color(0xFFFFFFFF)

@Immutable
data class MoimColors(
    val background: Color,
    val black: Color,
    val white: Color,
    val transparent: Color
)

internal val lightMoimColors = MoimColors(
    background = White,
    black = Black,
    white = White,
    transparent = Transparent
)

internal val darkMoimColors = MoimColors(
    background = Black,
    black = Black,
    white = White,
    transparent = Transparent
)

internal val lightMaterialScheme = lightColorScheme(
    background = lightMoimColors.background
)

internal val darkMaterialScheme = darkColorScheme(
    background = darkMoimColors.background
)

internal val LocalMoimColors = staticCompositionLocalOf {
    lightMoimColors
}