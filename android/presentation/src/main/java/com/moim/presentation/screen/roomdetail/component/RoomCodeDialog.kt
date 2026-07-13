package com.moim.presentation.screen.roomdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.moim.presentation.theme.MoimPadding
import com.moim.presentation.theme.MoimTheme

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
                    color = MoimTheme.colors.background,
                    shape = MoimTheme.shapes.roundedSmall
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