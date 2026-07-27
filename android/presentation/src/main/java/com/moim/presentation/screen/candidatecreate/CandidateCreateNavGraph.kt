package com.moim.presentation.screen.candidatecreate

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.moim.presentation.navigation.CandidateCreate

fun NavBackStack<NavKey>.navigateToCandidateCreate(roomId: Long) {
    add(CandidateCreate(roomId))
}

fun EntryProviderScope<NavKey>.candidateCreate(
    metadata: Map<String, Any>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    entry<CandidateCreate>(metadata = metadata) { route ->
        CandidateCreateScreen(
            route = route,
            onNavigateBack = onNavigateBack,
            modifier = modifier
        )
    }
}