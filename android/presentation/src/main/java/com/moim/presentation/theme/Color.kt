package com.moim.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val primary = Color(0xFF7850C8)
val surface = Color(0xFFF0E6FF)

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Transparent = Color(0x00000000)

@Immutable
data class MoimColors(
    val primary: Color,
    val surface: Color,
    val background: Color,
    val black: Color,
    val white: Color,
    val transparent: Color
)

internal val lightMoimColors = MoimColors(
    primary = primary,
    surface = surface,
    background = White,
    black = Black,
    white = White,
    transparent = Transparent
)

internal val darkMoimColors = MoimColors(
    primary = primary,
    surface = surface,
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