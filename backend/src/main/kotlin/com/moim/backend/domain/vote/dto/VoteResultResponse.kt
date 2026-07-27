package com.moim.backend.domain.vote.dto

import com.moim.backend.domain.vote.model.Category

data class VoteResultResponse(
    val category: Category,
    val rankings: List<VoteRankDto>
)