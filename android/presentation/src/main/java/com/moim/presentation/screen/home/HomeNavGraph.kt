package com.moim.presentation.screen.home

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.navigation.Home

fun NavBackStack<NavKey>.navigateToHome() {
    clear()
    add(Home)
}

fun EntryProviderScope<NavKey>.home(
    metadata: Map<String, Any>,
    onNavigateToRoomDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    entry<Home>(metadata = metadata) {
        HomeScreen(
            onNavigateToRoomDetail = { onNavigateToRoomDetail(it) },
            modifier = modifier
        )
    }
}