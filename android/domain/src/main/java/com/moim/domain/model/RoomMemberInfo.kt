package com.moim.domain.model

data class RoomMemberInfo(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val role: String
)