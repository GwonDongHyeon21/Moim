package com.moim.data.feature.vote.model

import com.moim.domain.feature.vote.model.CategoryInfo
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val name: String
)

fun CategoryResponse.toDomain() = CategoryInfo(
    name = name
)