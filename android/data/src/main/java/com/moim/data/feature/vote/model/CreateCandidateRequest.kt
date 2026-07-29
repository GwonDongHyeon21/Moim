package com.moim.data.feature.vote.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateCandidateRequest(
    val category: String,
    val content: String
)