package com.moim.backend.domain.vote.dto

import com.moim.backend.core.model.Category

data class CandidateResponse(
    val id: Long,
    val category: Category,
    val content: String,
    val creatorNickname: String,
    val isVotedByMe: Boolean
)