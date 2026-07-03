package com.moim.data.feature.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String? = null,
    val createdAt: String
)