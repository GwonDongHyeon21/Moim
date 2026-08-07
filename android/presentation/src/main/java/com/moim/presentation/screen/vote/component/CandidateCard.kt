package com.moim.presentation.screen.vote.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moim.presentation.R
import com.moim.presentation.screen.vote.component.CandidateCard.BORDER_COLOR_ALPHA
import com.moim.presentation.screen.vote.component.CandidateCard.borderWidth
import com.moim.presentation.screen.vote.component.CandidateCard.fontSize
import com.moim.presentation.screen.vote.component.CandidateCard.iconButtonSize
import com.moim.presentation.screen.vote.model.CandidateUiModel
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.theme.MoimTheme
import com.moim.presentation.util.DummyData

private object CandidateCard {
    val borderWidth = 1.dp
    const val BORDER_COLOR_ALPHA = 0.5f

    val fontSize = 32.sp

    val iconButtonSize = 64.dp
}

@Composable
fun CandidateCard(
    candidate: CandidateUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MoimPadding.PaddingMedium)
            .border(
                width = borderWidth,
                color = MoimTheme.colors.gray.copy(alpha = BORDER_COLOR_ALPHA),
                shape = MoimTheme.shapes.roundedLarge
            )
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(
                space = MoimSpace.SpaceLarge,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = candidate.category,
                color = MoimTheme.colors.white,
                modifier = Modifier
                    .background(
                        color = MoimTheme.colors.black,
                        shape = MoimTheme.shapes.roundedSmall
                    )
                    .padding(
                        horizontal = MoimPadding.PaddingMedium,
                        vertical = MoimPadding.PaddingXSmall
                    )
            )

            Text(
                text = candidate.content,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
            )

            if (candidate.isVotedByMe) {
                Icon(
                    painter = painterResource(R.drawable.favorite_fill_24),
                    contentDescription = null,
                    tint = MoimTheme.colors.red
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.favorite_24),
                    contentDescription = null
                )
            }
        }

        if (candidate.isVotedByMe) {
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = MoimPadding.PaddingMedium)
                    .size(iconButtonSize)
                    .border(
                        width = borderWidth,
                        color = MoimTheme.colors.gray,
                        shape = MoimTheme.shapes.roundedMax
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close_24),
                    contentDescription = stringResource(R.string.select_cancel_description)
                )
            }
        } else {
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = MoimPadding.PaddingMedium)
                    .size(iconButtonSize)
                    .border(
                        width = borderWidth,
                        color = MoimTheme.colors.gray,
                        shape = MoimTheme.shapes.roundedMax
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check_24),
                    contentDescription = stringResource(R.string.select_contentDescription)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CandidateCardPreview() {
    CandidateCard(
        candidate = DummyData.dummyCandidates.first(),
        onClick = {}
    )
}