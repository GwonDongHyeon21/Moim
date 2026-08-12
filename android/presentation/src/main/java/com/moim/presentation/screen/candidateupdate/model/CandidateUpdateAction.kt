package com.moim.presentation.screen.candidateupdate.model

interface CandidateUpdateAction {

    data object NavigateBack : CandidateUpdateAction

    data class OnCategorySelected(
        val num: Int,
        val category: String
    ) : CandidateUpdateAction

    data class OnContentChanged(
        val num: Int,
        val content: String
    ) : CandidateUpdateAction

    data object UpdateCandidate : CandidateUpdateAction
}