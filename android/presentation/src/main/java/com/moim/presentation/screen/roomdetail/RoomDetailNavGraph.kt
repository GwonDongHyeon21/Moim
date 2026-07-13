package com.moim.presentation.screen.roomdetail

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.navigation.RoomDetail

fun NavBackStack<NavKey>.navigateToRoomDetail(roomId: Long) {
    add(RoomDetail(roomId))
}

fun EntryProviderScope<NavKey>.roomDetail(
    metadata: Map<String, Any>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    entry<RoomDetail>(metadata = metadata) { route ->
        RoomDetailScreen(
            route = route,
            onNavigateBack = onNavigateBack,
            modifier = modifier
        )
    }
}