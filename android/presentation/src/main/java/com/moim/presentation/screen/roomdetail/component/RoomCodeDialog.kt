package com.moim.presentation.screen.roomdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.moim.presentation.theme.MoimPadding

@Composable
fun RoomCodeDialog(
    roomCode: String,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Text(
            text = roomCode,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.small
                )
                .padding(MoimPadding.PaddingSmall)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoomCodeDialogPreview() {
    RoomCodeDialog(
        roomCode = "asdf",
        onDismissRequest = {}
    )
}