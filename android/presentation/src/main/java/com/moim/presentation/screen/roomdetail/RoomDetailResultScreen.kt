package com.moim.presentation.screen.roomdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.screen.roomdetail.RoomDetailResultScreen.BAR_WIDTH_RATIO
import com.moim.presentation.screen.roomdetail.RoomDetailResultScreen.JOIN_TO_STRING_SEPARATOR
import com.moim.presentation.screen.roomdetail.RoomDetailResultScreen.MAX_LINES
import com.moim.presentation.screen.roomdetail.component.VoteCountRatioBar
import com.moim.presentation.screen.roomdetail.component.VoteResultDetailDialog
import com.moim.presentation.screen.roomdetail.model.VoteRankUiModel
import com.moim.presentation.screen.roomdetail.model.VoteResultUiModel
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.util.DummyData

private object RoomDetailResultScreen {
    const val MAX_LINES = 1
    const val BAR_WIDTH_RATIO = 0.3f

    const val JOIN_TO_STRING_SEPARATOR = ", "

}

@Composable
fun RoomDetailResultScreen(
    currentMemberCount: Int,
    voteResult: List<VoteResultUiModel>,
    modifier: Modifier = Modifier
) {
    var showVoteResultDetail by remember { mutableStateOf(false) }
    var selectedRankings by remember { mutableStateOf(emptyList<VoteRankUiModel>()) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MoimSpace.SpaceSmall)
    ) {
        voteResult.forEach { (category, rankings) ->
            val maxVoteCount = rankings.maxOfOrNull { it.voteCount } ?: 0
            val topRankings = rankings.filter { it.voteCount == maxVoteCount }
            val topContents = topRankings.joinToString(JOIN_TO_STRING_SEPARATOR) { it.content }

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
                        maxLines = MAX_LINES
                    )
                    VoteCountRatioBar(
                        maxVoteCount = maxVoteCount,
                        currentMemberCount = currentMemberCount,
                        modifier = Modifier.fillMaxWidth(BAR_WIDTH_RATIO),
                        onClickDialog = {
                            selectedRankings = rankings
                            showVoteResultDetail = true
                        }
                    )
                } else {
                    Text(text = category)
                }
            }
        }
    }

    if (showVoteResultDetail) {
        VoteResultDetailDialog(
            onDismissRequest = { showVoteResultDetail = false },
            rankings = selectedRankings
        )
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