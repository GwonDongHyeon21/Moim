package com.moim.data.feature.room.model

import com.moim.domain.model.RoomMemberInfo
import kotlinx.serialization.Serializable

@Serializable
data class RoomMemberResponse(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val role: String
)

fun RoomMemberResponse.toDomain() = RoomMemberInfo(
    userId = userId,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    role = role
)