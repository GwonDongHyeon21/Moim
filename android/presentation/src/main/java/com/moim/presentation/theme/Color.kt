package com.moim.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val primary = Color(0xFF7850C8)
val surface = Color(0xFFF0E6FF)

val Black = Color(0xFF000000)
val Gray = Color(0xFF888888)
val LightGray = Color(0xFFCCCCCC)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFFF0000)
val Transparent = Color(0x00000000)

@Immutable
data class MoimColors(
    val primary: Color,
    val surface: Color,
    val background: Color,
    val black: Color,
    val gray: Color,
    val lightGray: Color,
    val white: Color,
    val red: Color,
    val transparent: Color
)

internal val lightMoimColors = MoimColors(
    primary = primary,
    surface = surface,
    background = White,
    black = Black,
    gray = Gray,
    lightGray = LightGray,
    white = White,
    red = Red,
    transparent = Transparent
)

internal val darkMoimColors = MoimColors(
    primary = primary,
    surface = surface,
    background = Black,
    black = Black,
    gray = Gray,
    lightGray = LightGray,
    white = White,
    red = Red,
    transparent = Transparent
)

internal val lightMaterialScheme = lightColorScheme(
    primary = lightMoimColors.primary,
    surface = lightMoimColors.surface,
    background = lightMoimColors.background
)

internal val darkMaterialScheme = darkColorScheme(
    primary = darkMoimColors.primary,
    surface = darkMoimColors.surface,
    background = darkMoimColors.background
)

internal val LocalMoimColors = staticCompositionLocalOf {
    lightMoimColors
}