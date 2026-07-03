package com.moim.presentation.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class MoimShapes(
    val roundedSmall: RoundedCornerShape,
    val roundedMedium: RoundedCornerShape,
    val roundedLarge: RoundedCornerShape,
    val roundedMax: RoundedCornerShape
)

internal val moimShapes = MoimShapes(
    roundedSmall = RoundedCornerShape(size = 12.dp),
    roundedMedium = RoundedCornerShape(size = 16.dp),
    roundedLarge = RoundedCornerShape(size = 20.dp),
    roundedMax = CircleShape
)

internal val LocalMoimShapes = staticCompositionLocalOf {
    moimShapes
}