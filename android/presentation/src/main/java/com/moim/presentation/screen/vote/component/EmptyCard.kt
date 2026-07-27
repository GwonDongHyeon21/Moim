package com.moim.presentation.screen.vote.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moim.presentation.R
import com.moim.presentation.screen.vote.component.EmptyCard.BORDER_COLOR_ALPHA
import com.moim.presentation.screen.vote.component.EmptyCard.borderWidth
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimTheme

private object EmptyCard {
    val borderWidth = 1.dp
    const val BORDER_COLOR_ALPHA = 0.5f
}

@Composable
fun EmptyCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MoimPadding.PaddingMedium)
            .border(
                width = borderWidth,
                color = MoimTheme.colors.gray.copy(alpha = BORDER_COLOR_ALPHA),
                shape = MoimTheme.shapes.roundedLarge
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.empty_candidate),
            color = MoimTheme.colors.gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyCardPreview() {
    EmptyCard()
}