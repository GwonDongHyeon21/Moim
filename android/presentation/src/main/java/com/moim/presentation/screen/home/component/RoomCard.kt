package com.moim.presentation.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R
import com.moim.presentation.model.RoomInfoUiModel
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.util.DummyData

@Composable
fun RoomCard(
    room: RoomInfoUiModel,
    onCardClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var dropdownMenuExpanded by remember { mutableStateOf(false) }

    Card(
        onClick = { onCardClick() },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(MoimPadding.PaddingSmall)) {
                Text(text = room.title)
                Text(text = room.description.toString())
            }

            if (room.isHost) {
                Box {
                    IconButton(onClick = { dropdownMenuExpanded = true }) {
                        Icon(
                            painter = painterResource(R.drawable.more_vert_24),
                            contentDescription = stringResource(R.string.more_options_description)
                        )
                    }

                    DropdownMenu(
                        expanded = dropdownMenuExpanded,
                        onDismissRequest = { dropdownMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.delete)) },
                            onClick = {
                                dropdownMenuExpanded = false
                                onDeleteClick()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomCardPreview() {
    RoomCard(
        room = DummyData.dummyRooms.first(),
        onCardClick = {},
        onDeleteClick = {}
    )
}