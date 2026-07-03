package com.moim.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moim.presentation.navigation.MoimNav
import com.moim.presentation.navigation.MoimNavigator

@Composable
fun MoimApp(navigator: MoimNavigator) {
    Scaffold(
        bottomBar = {

        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MoimNav(
                navigator = navigator,
                innerPadding = innerPadding
            )
        }
    }
}