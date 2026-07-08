package com.moim.data.feature.user.model

import com.moim.domain.model.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String? = null,
    val createdAt: String
)

fun UserResponse.toDomain() = UserInfo(
    id = id,
    email = email,
    nickname = nickname,
    profileImageUrl = profileImageUrl
)