package com.moim.presentation.model

import com.moim.domain.feature.vote.model.CategoryInfo

data class CategoryUiModel(
    val name: String
)

fun CategoryInfo.toUiModel() = CategoryUiModel(
    name = name
)