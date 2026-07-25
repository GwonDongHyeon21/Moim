package com.moim.backend.domain.vote.model

data class CreateCandidateRequest(
    val category: Category,
    val content: String
)