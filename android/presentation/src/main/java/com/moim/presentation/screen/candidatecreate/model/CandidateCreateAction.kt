package com.moim.presentation.screen.candidatecreate.model

interface CandidateCreateAction {

    data class OnCategorySelected(val category: String) : CandidateCreateAction

    data class OnContentChanged(val content: String) : CandidateCreateAction

    data object CreateCandidate : CandidateCreateAction

    data object NavigateBack : CandidateCreateAction
}