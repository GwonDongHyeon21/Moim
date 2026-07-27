package com.moim.data.feature.vote.model

import com.moim.domain.feature.vote.model.VoteResultInfo
import kotlinx.serialization.Serializable

@Serializable
data class VoteResultResponse(
    val category: String,
    val rankings: List<VoteRankResponse>
)

fun VoteResultResponse.toDomain() = VoteResultInfo(
    category = category,
    rankings = rankings.map { it.toDomain() }
)