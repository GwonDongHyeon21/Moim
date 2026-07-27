package com.moim.presentation.screen.vote.model

interface VoteAction {

    data class CastVote(val candidateId: Long) : VoteAction

    data object ResetVote : VoteAction

    data object NavigateBack : VoteAction
}