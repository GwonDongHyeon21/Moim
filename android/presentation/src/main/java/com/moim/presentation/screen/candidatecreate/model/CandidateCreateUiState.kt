package com.moim.presentation.screen.candidatecreate.model

data class CandidateCreateUiState(
    val isLoading: Boolean = false,
    val selectedCategory: String = "",
    val content: String = ""
)