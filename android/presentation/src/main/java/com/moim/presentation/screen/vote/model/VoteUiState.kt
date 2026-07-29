package com.moim.presentation.screen.vote.model

data class VoteUiState(
    val isLoading: Boolean = false,
    val candidates: List<CandidateUiModel> = emptyList()
)