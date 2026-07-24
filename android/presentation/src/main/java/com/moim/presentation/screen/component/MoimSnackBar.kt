package com.moim.presentation.screen.component

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MoimSnackBar(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier.navigationBarsPadding()
    ) { snackBarData ->
        MoimSnackBarContent(snackBarData.visuals.message)
    }
}

@Composable
fun MoimSnackBarContent(
    message: String,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier,
        content = {
            Text(text = message)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun MoimSnackBarContentPreview() {
    MoimSnackBarContent("test error message")
}