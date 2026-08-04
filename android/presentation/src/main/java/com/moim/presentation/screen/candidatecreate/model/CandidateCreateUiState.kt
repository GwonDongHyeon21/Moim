package com.moim.presentation.screen.candidatecreate.model

data class CandidateCreateUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryUiModel> = emptyList(),
    val selectedCategory: String = "",
    val content: String = ""
)