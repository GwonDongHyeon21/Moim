package com.moim.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun MoimTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val moimColorScheme = if (darkTheme) darkMoimColors else lightMoimColors
    val materialColorScheme = if (darkTheme) darkMaterialScheme else lightMaterialScheme

    CompositionLocalProvider(
        LocalMoimColors provides moimColorScheme,
//        LocalMoimTypography provides moimTypography,
        LocalMoimShapes provides moimShapes
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            content = content
        )
    }
}

object MoimTheme {
    val colors: MoimColors
        @Composable
        get() = LocalMoimColors.current

    val typography: MoimTypography
        @Composable
        get() = LocalMoimTypography.current

    val shapes: MoimShapes
        @Composable
        get() = LocalMoimShapes.current
}