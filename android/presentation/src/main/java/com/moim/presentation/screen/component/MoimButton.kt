package com.moim.presentation.screen.component

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moim.presentation.screen.component.MoimButton.ButtonHeight

private object MoimButton {
    val ButtonHeight = 60.dp
}

@Composable
fun MoimButton(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.heightIn(min = ButtonHeight)
    ) {
        Text(text = value)
    }
}

@Preview(showBackground = true)
@Composable
fun MoimButtonPreview() {
    MoimButton(
        value = "test",
        onClick = {}
    )
}