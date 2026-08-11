package com.moim.presentation.screen.vote.model

import com.moim.presentation.model.CandidateUiModel

data class VoteUiState(
    val isLoading: Boolean = false,
    val candidates: List<CandidateUiModel> = emptyList()
)