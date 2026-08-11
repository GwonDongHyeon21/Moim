package com.moim.presentation.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moim.presentation.theme.MoimTheme

@Composable
fun MoimBottomBarButton(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    BottomAppBar(
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = onClick
        ),
        containerColor = if (enabled) MoimTheme.colors.primary else MoimTheme.colors.lightGray
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = value)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoimBottomBarButtonPreview() {
    MoimBottomBarButton(
        value = "test",
        onClick = {}
    )
}