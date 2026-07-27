package com.moim.domain.feature.vote.model

data class CandidateInfo(
    val id: Long,
    val category: String,
    val content: String,
    val creatorNickname: String,
    val isVotedByMe: Boolean
)