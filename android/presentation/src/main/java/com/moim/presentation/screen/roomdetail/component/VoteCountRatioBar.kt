package com.moim.presentation.screen.roomdetail.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moim.presentation.R
import com.moim.presentation.screen.roomdetail.component.VoteCountRatioBar.COLOR_ALPHA
import com.moim.presentation.screen.roomdetail.component.VoteCountRatioBar.borderWidth
import com.moim.presentation.theme.MoimTheme

private object VoteCountRatioBar {
    val borderWidth = 1.dp
    const val COLOR_ALPHA = 0.5f
}

@Composable
fun VoteCountRatioBar(
    currentMemberCount: Int,
    maxVoteCount: Int,
    onClickDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val voteRatio = maxVoteCount.toFloat() / currentMemberCount.toFloat()
    val color = MoimTheme.colors.primary.copy(COLOR_ALPHA)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .weight(1f)
                .border(
                    width = borderWidth,
                    color = MoimTheme.colors.gray
                )
                .drawBehind {
                    drawRect(
                        color = color,
                        size = size.copy(width = size.width * voteRatio)
                    )
                }
        ) {
            Text(text = "$maxVoteCount / $currentMemberCount")
        }

        Icon(
            painter = painterResource(R.drawable.chevron_right_24),
            contentDescription = stringResource(R.string.vote_result_detail_description),
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = onClickDialog
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VoteCountRatioBarPreview() {
    VoteCountRatioBar(
        currentMemberCount = 10,
        maxVoteCount = 6,
        onClickDialog = {}
    )
}