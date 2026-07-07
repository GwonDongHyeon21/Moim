package com.moim.presentation.screen.login

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.navigation.Login

fun NavBackStack<NavKey>.navigateToLogin() {
    clear()
    add(Login)
}

fun EntryProviderScope<NavKey>.login(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    entry<Login> {
        LoginScreen(
            onNavigateToHome = onNavigateToHome,
            modifier = modifier
        )
    }
}