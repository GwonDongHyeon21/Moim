package com.moim.data.feature.vote.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCandidateRequest(
    val id: Long,
    val category: String,
    val content: String
)