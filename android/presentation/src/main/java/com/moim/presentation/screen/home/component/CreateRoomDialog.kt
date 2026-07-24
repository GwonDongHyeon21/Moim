package com.moim.presentation.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoomDialog(
    title: String,
    description: String,
    selectedDateTime: LocalDateTime?,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onDateTimeSelected: (LocalDateTime) -> Unit,
    onConfirm: (CreateRoomParams) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var tempDate by remember { mutableStateOf<LocalDate?>(null) }

    val uiFormatter =
        DateTimeFormatter.ofPattern(stringResource(R.string.ui_time_format), Locale.KOREA)
    val isoFormatter =
        DateTimeFormatter.ofPattern(stringResource(R.string.iso_time_format), Locale.KOREA)

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
                    onValueChange = { onTitleChanged(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = description,
                    onValueChange = { onDescriptionChanged(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(MoimSpace.SpaceXSmall))
                OutlinedTextField(
                    value = selectedDateTime?.format(uiFormatter)
                        ?: stringResource(R.string.please_select_deadline),
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    enabled = false,
                    readOnly = true,
                    label = { Text(text = stringResource(R.string.deadline_title)) },
                )

                Spacer(modifier = Modifier.height(MoimSpace.SpaceSmall))
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
                                    description = description,
                                    deadline = selectedDateTime!!.format(isoFormatter)
                                )
                            )
                        },
                        enabled = title.isNotBlank() && selectedDateTime != null,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )

    if (showDatePicker) {
        RoomDatePickerDialog(
            initialTimeInMillis = System.currentTimeMillis(),
            onConfirm = { year, month, day ->
                tempDate = LocalDate.of(year, month, day)

                showDatePicker = false
                showTimePicker = true
            },
            onDismissRequest = { showDatePicker = false }
        )
    }

    if (showTimePicker) {
        RoomTimePickerDialog(
            initialHour = LocalDateTime.now().hour,
            initialMinute = LocalDateTime.now().minute,
            onConfirm = { hour, minute ->
                tempDate?.let { date ->
                    onDateTimeSelected(date.atTime(hour, minute, 0))
                }

                showTimePicker = false
            },
            onDismissRequest = { showTimePicker = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoomOptionDialogPreview() {
    CreateRoomDialog(
        title = "test title",
        description = "test description",
        selectedDateTime = null,
        onTitleChanged = {},
        onDescriptionChanged = {},
        onDateTimeSelected = {},
        onConfirm = {},
        onDismissRequest = {}
    )
}