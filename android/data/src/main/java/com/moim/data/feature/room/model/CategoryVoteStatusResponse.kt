package com.moim.data.feature.room.model

import com.moim.domain.feature.room.model.CategoryVoteStatusInfo
import kotlinx.serialization.Serializable

@Serializable
data class CategoryVoteStatusResponse(
    val category: String,
    val isVoted: Boolean
)

fun CategoryVoteStatusResponse.toDomain() = CategoryVoteStatusInfo(
    category = category,
    isVoted = isVoted
)