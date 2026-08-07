package com.moim.presentation.screen.roomdetail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailTopBar(
    value: String,
    onClickRoomCode: () -> Unit,
    onClickUpdate: () -> Unit,
    onClickDelete: () -> Unit,
    onClickNavigationIcon: () -> Unit
) {
    var dropdownMenuExpanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = { Text(text = value) },
        navigationIcon = {
            IconButton(onClick = onClickNavigationIcon) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_24),
                    contentDescription = null
                )
            }
        },
        actions = {
            Box {
                IconButton(onClick = { dropdownMenuExpanded = true }) {
                    Icon(
                        painter = painterResource(R.drawable.more_vert_24),
                        contentDescription = null
                    )
                }

                DropdownMenu(
                    expanded = dropdownMenuExpanded,
                    onDismissRequest = { dropdownMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.room_detail_code)) },
                        onClick = {
                            dropdownMenuExpanded = false
                            onClickRoomCode()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.room_detail_update)) },
                        onClick = {
                            dropdownMenuExpanded = false
                            onClickUpdate()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.room_detail_delete)) },
                        onClick = {
                            dropdownMenuExpanded = false
                            onClickDelete()
                        }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RoomDetailTopBarPreview() {
    RoomDetailTopBar(
        value = "test",
        onClickRoomCode = {},
        onClickUpdate = {},
        onClickDelete = {},
        onClickNavigationIcon = {}
    )
}