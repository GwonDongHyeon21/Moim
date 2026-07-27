package com.moim.backend.domain.vote.model

data class CandidateResponse(
    val id: Long,
    val category: Category,
    val content: String,
    val creatorNickname: String,
    val isVotedByMe: Boolean
)