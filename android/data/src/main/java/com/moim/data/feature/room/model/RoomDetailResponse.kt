package com.moim.data.feature.room.model

import com.moim.domain.model.RoomDetailInfo
import kotlinx.serialization.Serializable

@Serializable
data class RoomDetailResponse(
    val roomInfo: RoomResponse,
    val role: String,
    val members: List<RoomMemberResponse>
)

fun RoomDetailResponse.toDomain() = RoomDetailInfo(
    roomInfo = roomInfo.toDomain(),
    role = role,
    members = members.map { it.toDomain() }
)