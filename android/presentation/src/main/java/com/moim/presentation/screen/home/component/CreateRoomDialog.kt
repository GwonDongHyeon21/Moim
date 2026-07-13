package com.moim.presentation.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moim.domain.model.CreateRoomParams
import com.moim.presentation.R
import com.moim.presentation.screen.component.MoimButton
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimSpace
import com.moim.presentation.theme.MoimTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoomDialog(
    onConfirm: (CreateRoomParams) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier
                    .background(
                        color = MoimTheme.colors.background,
                        shape = MoimTheme.shapes.roundedSmall
                    )
                    .padding(MoimPadding.PaddingSmall)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(MoimSpace.SpaceXSmall))
                Row {
                    MoimButton(
                        value = stringResource(R.string.cancel),
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1f)
                    )
                    MoimButton(
                        value = stringResource(R.string.add),
                        onClick = {
                            onConfirm(
                                CreateRoomParams(
                                    title = title,
                                    description = description
                                )
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RoomOptionDialogPreview() {
    CreateRoomDialog(
        onConfirm = {},
        onDismissRequest = {}
    )
}