package com.moim.backend.domain.vote.dto

import com.moim.backend.core.model.Category

data class CategoryResponse(
    val name: String
) {
    companion object {
        fun from(category: Category): CategoryResponse {
            return CategoryResponse(
                name = category.name
            )
        }
    }
}