package com.moim.domain.feature.vote.model

data class UpdateCandidateParams(
    val id: Long,
    val category: String,
    val content: String
)