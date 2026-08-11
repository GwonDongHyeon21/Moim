package com.moim.presentation.screen.vote

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.result.LocalResultEventBus
import com.moim.presentation.navigation.Vote

data class VoteResult(val shouldRefresh: Boolean)

fun NavBackStack<NavKey>.navigateToVote(roomId: Long, category: String) {
    add(Vote(roomId, category))
}

fun EntryProviderScope<NavKey>.vote(
    metadata: Map<String, Any>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    entry<Vote>(metadata = metadata) { route ->
        val resultBus = LocalResultEventBus.current

        VoteScreen(
            route = route,
            onNavigateBack = onNavigateBack,
            onNavigateBackRefresh = {
                resultBus.sendResult(VoteResult(true))
                onNavigateBack()
            },
            modifier = modifier
        )
    }
}