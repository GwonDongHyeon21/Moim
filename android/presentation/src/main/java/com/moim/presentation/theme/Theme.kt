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
        LocalMoimColors provides lightMoimColors, //moimColorScheme, 우선 라이트 색상만 적용
//        LocalMoimTypography provides moimTypography,
        LocalMoimShapes provides moimShapes
    ) {
        MaterialTheme(
            colorScheme = lightMaterialScheme, //materialColorScheme, 우선 라이트 모드로 고정
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