package com.moim.presentation.screen.component.dialog

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun RoomDatePickerDialog(
    initialTimeInMillis: Long,
    onConfirm: (year: Int, month: Int, dayOfMonth: Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val todayTimeMillis = remember {
        LocalDate.now()
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialTimeInMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayTimeMillis
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDate()

                        onConfirm(
                            localDate.year,
                            localDate.monthValue,
                            localDate.dayOfMonth
                        )
                    }
                }
            ) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        modifier = modifier,
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RoomDatePickerDialogPreview() {
    RoomDatePickerDialog(
        initialTimeInMillis = 0L,
        onConfirm = { _, _, _ -> },
        onDismissRequest = {}
    )
}