package com.moim.presentation.model

import com.moim.domain.feature.vote.model.CandidateInfo

data class CandidateUiModel(
    val id: Long,
    val category: String,
    val content: String,
    val creatorNickname: String,
    val isVotedByMe: Boolean
)

fun CandidateInfo.toUiModel() = CandidateUiModel(
    id = id,
    category = category,
    content = content,
    creatorNickname = creatorNickname,
    isVotedByMe = isVotedByMe
)