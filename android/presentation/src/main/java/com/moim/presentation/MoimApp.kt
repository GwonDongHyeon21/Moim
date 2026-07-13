package com.moim.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.navigation.MoimNav
import com.moim.presentation.navigation.rememberMoimNavigator

@Composable
fun MoimApp(viewModel: MainViewModel = hiltViewModel()) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    val navigator = rememberMoimNavigator(startDestination = startDestination)

    Scaffold(
        bottomBar = {

        },
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        MoimNav(
            navigator = navigator,
            innerPadding = innerPadding
        )
    }
}