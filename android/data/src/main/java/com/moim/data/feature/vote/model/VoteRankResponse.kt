package com.moim.data.feature.vote.model

import com.moim.domain.feature.vote.model.VoteRankInfo
import kotlinx.serialization.Serializable

@Serializable
data class VoteRankResponse(
    val candidate: CandidateResponse,
    val voteCount: Int
)

fun VoteRankResponse.toDomain() = VoteRankInfo(
    candidate = candidate.toDomain(),
    voteCount = voteCount
)