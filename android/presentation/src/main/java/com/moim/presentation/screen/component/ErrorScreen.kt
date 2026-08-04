package com.moim.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.R
import com.moim.presentation.screen.component.ErrorScreen.MAX_LINES
import com.moim.presentation.theme.MoimSpace

private object ErrorScreen {
    const val MAX_LINES = 3
}

@Composable
fun ErrorScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    message: String? = null
) {
    val errorMessage = message ?: stringResource(R.string.error_message)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            overflow = TextOverflow.Ellipsis,
            maxLines = MAX_LINES
        )

        Spacer(modifier = Modifier.height(MoimSpace.SpaceSmall))
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen(onRetry = {})
}