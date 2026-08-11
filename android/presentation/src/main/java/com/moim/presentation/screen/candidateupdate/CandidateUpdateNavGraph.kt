package com.moim.presentation.screen.candidateupdate

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.navigation.CandidateUpdate

fun NavBackStack<NavKey>.navigateToCandidateUpdate(category: String) {
    add(CandidateUpdate(category))
}

fun EntryProviderScope<NavKey>.candidateUpdate(
    metadata: Map<String, Any>,
    modifier: Modifier = Modifier
) {
    entry<CandidateUpdate>(metadata = metadata) { route ->
        CandidateUpdateScreen(
            route = route,
            onNavigateBack = {},
            modifier = modifier
        )
    }
}