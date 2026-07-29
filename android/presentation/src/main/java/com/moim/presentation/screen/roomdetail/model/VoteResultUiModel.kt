package com.moim.presentation.screen.roomdetail.model

import com.moim.domain.feature.vote.model.VoteResultInfo

data class VoteResultUiModel(
    val category: String,
    val rankings: List<VoteRankUiModel>
)

fun VoteResultInfo.toUiModel() = VoteResultUiModel(
    category = category,
    rankings = rankings.map { it.toUiModel() }
)