package com.moim.presentation.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R
import com.moim.presentation.screen.home.model.RoomFilterStatus
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimTheme

@Composable
fun RoomFilterTab(
    selectedStatus: RoomFilterStatus,
    onStatusSelected: (RoomFilterStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MoimTheme.colors.background)
    ) {
        RoomFilterStatus.entries.forEach { status ->
            val isSelected = selectedStatus == status

            val text = when (status) {
                RoomFilterStatus.ONGOING -> stringResource(R.string.on_going)
                RoomFilterStatus.CLOSED -> stringResource(R.string.end)
            }

            val backgroundColor = if (isSelected) {
                MoimTheme.colors.surface
            } else {
                MoimTheme.colors.surface.copy(alpha = 0f)
            }
            val textColor = if (isSelected) {
                MoimTheme.colors.primary
            } else {
                MoimTheme.colors.black
            }

            Card(
                onClick = { onStatusSelected(status) },
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = backgroundColor,
                    contentColor = textColor
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MoimPadding.PaddingSmall),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = text)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RoomFilterTabPreview() {
    RoomFilterTab(
        selectedStatus = RoomFilterStatus.ONGOING,
        onStatusSelected = { }
    )
}