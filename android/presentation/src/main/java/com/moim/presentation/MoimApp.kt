package com.moim.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moim.presentation.navigation.Login
import com.moim.presentation.navigation.MainBottomBarRoute
import com.moim.presentation.navigation.MoimNav
import com.moim.presentation.navigation.rememberMoimNavigator
import com.moim.presentation.screen.component.MoimBottomBar
import com.moim.presentation.screen.component.MoimProgressIndicator
import com.moim.presentation.screen.component.MoimSnackBar
import com.moim.presentation.util.collectWithLifecycle
import com.moim.presentation.util.snackbar.SnackBarManager

@Composable
fun MoimApp(
    snackBarManager: SnackBarManager,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val isLogin by viewModel.isLogin.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

    if (isLogin == null) {
        MoimProgressIndicator()

        return
    }

    val startDestination = remember {
        if (isLogin == true) {
            MainBottomBarRoute.HOME.route
        } else {
            Login
        }
    }

    val navigator = rememberMoimNavigator(startDestination = startDestination)

    @SuppressLint("LocalContextGetResourceValueCall")
    snackBarManager.events.collectWithLifecycle { event ->
        snackBarHostState.showSnackbar(
            message = context.getString(event.messageResId),
            duration = SnackbarDuration.Short
        )
    }

    LaunchedEffect(isLogin) {
        if (isLogin == false && navigator.currentDestination != Login) {
            navigator.navigateToLogin()
        }
    }

    Scaffold(
        bottomBar = {
            MoimBottomBar(
                currentDestination = navigator.currentDestination,
                onNavigateToDestination = navigator::navigateToTab
            )
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