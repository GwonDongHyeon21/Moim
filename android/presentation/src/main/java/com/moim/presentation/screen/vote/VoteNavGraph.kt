package com.moim.presentation.screen.vote

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.navigation.Vote

fun NavBackStack<NavKey>.navigateToVote(roomId: Long, category: String) {
    add(Vote(roomId, category))
}

fun EntryProviderScope<NavKey>.vote(
    metadata: Map<String, Any>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    entry<Vote>(metadata = metadata) { route ->
        VoteScreen(
            route = route,
            onNavigateBack = onNavigateBack,
            modifier = modifier
        )
    }
}