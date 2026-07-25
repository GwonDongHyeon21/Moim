package com.moim.domain.feature.room.model

data class RoomMemberInfo(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val role: String
)