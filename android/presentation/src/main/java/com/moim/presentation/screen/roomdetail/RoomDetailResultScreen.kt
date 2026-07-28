package com.moim.presentation.screen.roomdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.screen.roomdetail.component.VoteCountRatioBar
import com.moim.presentation.screen.roomdetail.model.VoteResultUiModel
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.util.DummyData

@Composable
fun RoomDetailResultScreen(
    currentMemberCount: Int,
    voteResult: List<VoteResultUiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MoimSpace.SpaceSmall)
    ) {
        voteResult.forEach { (category, rankings) ->
            val maxVoteCount = rankings.maxOfOrNull { it.voteCount } ?: 0
            val topRankings = rankings.filter { it.voteCount == maxVoteCount }
            val topContents = topRankings.joinToString(", ") { it.content }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MoimPadding.PaddingMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (rankings.isNotEmpty()) {
                    Text(
                        text = "$category : $topContents",
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    VoteCountRatioBar(
                        maxVoteCount = maxVoteCount,
                        currentMemberCount = currentMemberCount,
                        modifier = Modifier.fillMaxWidth(0.3f)
                    )
                } else {
                    Text(text = category)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomDetailResultScreenPreview() {
    RoomDetailResultScreen(
        currentMemberCount = 10,
        voteResult = DummyData.dummyVoteResults
    )
}