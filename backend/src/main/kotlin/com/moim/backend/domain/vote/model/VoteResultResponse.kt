package com.moim.backend.domain.vote.model

data class VoteResultResponse(
    val category: Category,
    val rankings: List<VoteRankDto>
)