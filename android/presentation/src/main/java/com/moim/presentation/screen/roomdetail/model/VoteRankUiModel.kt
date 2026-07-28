package com.moim.presentation.screen.roomdetail.model

import com.moim.domain.feature.vote.model.VoteRankInfo

data class VoteRankUiModel(
    val content: String,
    val voteCount: Int
)

fun VoteRankInfo.toUiModel() = VoteRankUiModel(
    content = content,
    voteCount = voteCount
)