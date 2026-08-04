package com.moim.presentation.screen.roomdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R
import com.moim.presentation.screen.roomdetail.component.VoteResultDetailDialog.MAX_LINES
import com.moim.presentation.screen.roomdetail.model.VoteRankUiModel
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.theme.MoimTheme
import com.moim.presentation.util.DummyData

private object VoteResultDetailDialog {
    const val MAX_LINES = 1
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoteResultDetailDialog(
    onDismissRequest: () -> Unit,
    rankings: List<VoteRankUiModel>,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MoimTheme.colors.background,
                    shape = MoimTheme.shapes.roundedSmall
                )
                .padding(MoimPadding.PaddingSmall),
            verticalArrangement = Arrangement.spacedBy(MoimSpace.SpaceXSmall)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.vote_result_detail))
            }

            rankings.forEachIndexed { index, category ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "${index + 1}. ")
                    Text(
                        text = category.content,
                        maxLines = MAX_LINES,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.vote_result_index, category.voteCount),
                        color = MoimTheme.colors.gray
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun VoteResultDetailDialogPreview() {
    VoteResultDetailDialog(
        onDismissRequest = {},
        rankings = DummyData.dummyVoteResults.first().rankings
    )
}