package com.moim.backend.domain.vote.dto

import com.moim.backend.core.model.Category

data class CreateCandidateRequest(
    val category: Category,
    val content: String
)