package com.moim.presentation.screen.roomdetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R
import com.moim.presentation.screen.roomdetail.model.CategoryVoteStatusUiModel
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.theme.MoimTheme
import com.moim.presentation.util.DummyData

@Composable
fun CategoryCard(
    category: CategoryVoteStatusUiModel,
    onClickCard: () -> Unit,
    onClickUpdate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Card(
            onClick = onClickCard,
            shape = MoimTheme.shapes.roundedSmall,
            colors = CardDefaults.cardColors(containerColor = MoimTheme.colors.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MoimPadding.PaddingMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = category.category)
                if (category.isVoted) {
                    Icon(
                        painter = painterResource(R.drawable.check_circle_24),
                        contentDescription = null
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.circle_24),
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(MoimSpace.SpaceXSmall))
        Text(
            text = stringResource(R.string.room_detail_my_candidate_update),
            modifier = Modifier.clickable { onClickUpdate() },
            color = MoimTheme.colors.gray,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    CategoryCard(
        category = DummyData.dummyCategoryVoteStatus.first(),
        onClickCard = {},
        onClickUpdate = {}
    )
}