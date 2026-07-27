package com.moim.presentation.screen.roomdetail.model

import com.moim.domain.feature.room.model.CategoryVoteStatusInfo

data class CategoryVoteStatusUiModel(
    val category: String,
    val isVoted: Boolean
)

fun CategoryVoteStatusInfo.toUiModel() = CategoryVoteStatusUiModel(
    category = category,
    isVoted = isVoted
)