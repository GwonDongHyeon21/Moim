package com.moim.domain.feature.vote.model

data class VoteResultInfo(
    val category: String,
    val rankings: List<VoteRankInfo>
)