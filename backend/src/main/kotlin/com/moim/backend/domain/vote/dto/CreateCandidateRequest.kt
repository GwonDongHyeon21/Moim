package com.moim.backend.domain.vote.dto

import com.moim.backend.domain.vote.model.Category

data class CreateCandidateRequest(
    val category: Category,
    val content: String
)