package com.moim.data.feature.room.model

import kotlinx.serialization.Serializable

@Serializable
data class RoomMemberResponse(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val role: String
)