package com.moim.presentation.screen.roomdetail.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moim.presentation.screen.roomdetail.component.VoteCountRatioBar.borderWidth
import com.moim.presentation.screen.roomdetail.component.VoteCountRatioBar.COLOR_ALPHA
import com.moim.presentation.theme.MoimTheme

private object VoteCountRatioBar {
    val borderWidth = 1.dp
    const val COLOR_ALPHA = 0.5f
}

@Composable
fun VoteCountRatioBar(
    currentMemberCount: Int,
    maxVoteCount: Int,
    modifier: Modifier = Modifier
) {
    val voteRatio = maxVoteCount.toFloat() / currentMemberCount.toFloat()
    val color = MoimTheme.colors.primary.copy(COLOR_ALPHA)

    Box(
        modifier
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
}

@Preview(showBackground = true)
@Composable
fun VoteCountRatioBarPreview() {
    VoteCountRatioBar(
        currentMemberCount = 10,
        maxVoteCount = 6,
    )
}