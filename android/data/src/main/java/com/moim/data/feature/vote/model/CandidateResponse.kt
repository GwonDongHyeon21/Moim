package com.moim.data.feature.vote.model

import com.moim.domain.feature.vote.model.CandidateInfo
import kotlinx.serialization.Serializable

@Serializable
data class CandidateResponse(
    val id: Long,
    val category: String,
    val content: String,
    val creatorNickname: String,
    val isVotedByMe: Boolean
)

fun CandidateResponse.toDomain() = CandidateInfo(
    id = id,
    category = category,
    content = content,
    creatorNickname = creatorNickname,
    isVotedByMe = isVotedByMe
)