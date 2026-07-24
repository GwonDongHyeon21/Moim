package com.moim.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.moim.presentation.theme.MoimTheme

@Composable
fun MoimProgressIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MoimTheme.colors.white)
            .pointerInput(Unit) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}