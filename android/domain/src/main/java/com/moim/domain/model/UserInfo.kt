package com.moim.domain.model

data class UserInfo(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?
)