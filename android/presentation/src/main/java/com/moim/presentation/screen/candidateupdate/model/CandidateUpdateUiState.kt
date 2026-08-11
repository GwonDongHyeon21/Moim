package com.moim.presentation.screen.candidateupdate.model

import com.moim.presentation.model.CandidateUiModel
import com.moim.presentation.model.CategoryUiModel

data class CandidateUpdateUiState(
    val isLoading: Boolean = false,
    val candidates: List<CandidateUiModel> = emptyList(),
    val categories: List<CategoryUiModel> = emptyList(),
    val category: String = "",
    val content: String = ""
)