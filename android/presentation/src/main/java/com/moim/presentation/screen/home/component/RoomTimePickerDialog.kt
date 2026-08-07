package com.moim.presentation.screen.home.component

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomTimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    isToday: Boolean,
    onConfirm: (hour: Int, minute: Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = false
    )

    val isTimeEnabled = if (isToday) {
        val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
        selectedTime.isAfter(LocalTime.now())
    } else {
        true
    }

    TimePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = { onConfirm(timePickerState.hour, timePickerState.minute) },
                enabled = isTimeEnabled
            ) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        title = {},
        modifier = modifier,
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = stringResource(R.string.cancel))
            }
        },
    ) {
        TimePicker(state = timePickerState)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RoomTimePickerDialogPreview() {
    RoomTimePickerDialog(
        initialHour = 1,
        initialMinute = 2,
        isToday = true,
        onConfirm = { _, _ -> },
        onDismissRequest = {}
    )
}