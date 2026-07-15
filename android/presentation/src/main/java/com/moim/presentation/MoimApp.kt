package com.moim.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.navigation.MoimNav
import com.moim.presentation.navigation.rememberMoimNavigator
import com.moim.presentation.screen.component.MoimSnackBar
import com.moim.presentation.util.collectWithLifecycle
import com.moim.presentation.util.snackbar.SnackBarManager

@Composable
fun MoimApp(
    snackBarManager: SnackBarManager,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

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

    @SuppressLint("LocalContextGetResourceValueCall")
    snackBarManager.events.collectWithLifecycle { event ->
        snackBarHostState.showSnackbar(
            message = context.getString(event.messageResId),
            duration = SnackbarDuration.Short
        )
    }

    Scaffold(
        bottomBar = {

        },
        snackbarHost = { MoimSnackBar(hostState = snackBarHostState) },
        contentWindowInsets = WindowInsets()
    ) { innerPadding ->
        MoimNav(
            navigator = navigator,
            innerPadding = innerPadding
        )
    }
}