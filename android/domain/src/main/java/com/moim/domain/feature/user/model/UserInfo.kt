package com.moim.domain.feature.user.model

data class UserInfo(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?
)