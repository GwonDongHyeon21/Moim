package com.moim.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val NotoSansKR = FontFamily(
//    Font(R.font.noto_sans_kr_black, FontWeight.Black),
//    Font(R.font.noto_sans_kr_bold, FontWeight.Bold),
//    Font(R.font.noto_sans_kr_extra_bold, FontWeight.ExtraBold),
//    Font(R.font.noto_sans_kr_extra_light, FontWeight.ExtraLight),
//    Font(R.font.noto_sans_kr_light, FontWeight.Light),
//    Font(R.font.noto_sans_kr_medium, FontWeight.Medium),
//    Font(R.font.noto_sans_kr_regular, FontWeight.Normal),
//    Font(R.font.noto_sans_kr_semi_bold, FontWeight.SemiBold),
//    Font(R.font.noto_sans_kr_thin, FontWeight.Thin),
)

@Immutable
data class MoimTypography(
    val bodyLarge: TextStyle
)

internal val moimTypography = MoimTypography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )
)

internal val LocalMoimTypography = staticCompositionLocalOf {
    moimTypography
}