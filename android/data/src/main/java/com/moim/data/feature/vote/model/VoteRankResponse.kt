package com.moim.data.feature.vote.model

import com.moim.domain.feature.vote.model.VoteRankInfo
import kotlinx.serialization.Serializable

@Serializable
data class VoteRankResponse(
    val content: String,
    val voteCount: Int
)

fun VoteRankResponse.toDomain() = VoteRankInfo(
    content = content,
    voteCount = voteCount
)