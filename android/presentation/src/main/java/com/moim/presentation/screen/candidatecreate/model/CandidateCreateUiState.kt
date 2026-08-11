package com.moim.presentation.screen.candidatecreate.model

import com.moim.presentation.model.CategoryUiModel

data class CandidateCreateUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryUiModel> = emptyList(),
    val selectedCategory: String = "",
    val content: String = ""
)