package com.moim.presentation.screen.roomdetail

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.result.LocalResultEventBus
import com.moim.presentation.navigation.RoomDetail

data class RoomDetailResult(val shouldRefresh: Boolean)

fun NavBackStack<NavKey>.navigateToRoomDetail(roomId: Long) {
    add(RoomDetail(roomId))
}

fun EntryProviderScope<NavKey>.roomDetail(
    metadata: Map<String, Any>,
    onNavigateToVote: (roomId: Long, category: String) -> Unit,
    onNavigateToCandidateCreate: (Long) -> Unit,
    onNavigateToCandidateUpdate: (Long, String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    entry<RoomDetail>(metadata = metadata) { route ->
        val resultBus = LocalResultEventBus.current

        RoomDetailScreen(
            route = route,
            onNavigateToVote = onNavigateToVote,
            onNavigateToCandidateCreate = onNavigateToCandidateCreate,
            onNavigateToCandidateUpdate = onNavigateToCandidateUpdate,
            onNavigateBack = onNavigateBack,
            onNavigateBackRefresh = {
                resultBus.sendResult(RoomDetailResult(true))
                onNavigateBack()
            },
            modifier = modifier
        )
    }
}