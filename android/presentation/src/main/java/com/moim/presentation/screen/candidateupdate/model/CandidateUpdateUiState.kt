package com.moim.presentation.screen.candidateupdate.model

import com.moim.presentation.model.CandidateUiModel
import com.moim.presentation.model.CategoryUiModel

data class CandidateUpdateUiState(
    val isLoading: Boolean = false,
    val originalCandidates: List<CandidateUiModel> = emptyList(),
    val newCandidates: List<CandidateUiModel> = emptyList(),
    val categories: List<CategoryUiModel> = emptyList(),
    val category: List<String> = emptyList(),
    val content: List<String> = emptyList()
)