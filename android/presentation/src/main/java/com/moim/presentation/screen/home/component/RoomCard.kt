package com.moim.presentation.screen.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moim.presentation.model.RoomInfoUiModel
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.util.DummyData

@Composable
fun RoomCard(
    room: RoomInfoUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MoimPadding.PaddingSmall)
        ) {
            Text(text = room.title)
            Text(text = room.description.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomCardPreview() {
    RoomCard(
        room = DummyData.dummyRooms.first(),
        onClick = {}
    )
}