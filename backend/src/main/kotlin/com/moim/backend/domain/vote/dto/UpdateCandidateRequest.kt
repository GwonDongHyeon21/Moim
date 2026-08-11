package com.moim.backend.domain.vote.dto

import com.moim.backend.core.model.Category

data class UpdateCandidateRequest(
    val id: Long,
    val category: Category,
    val content: String
)