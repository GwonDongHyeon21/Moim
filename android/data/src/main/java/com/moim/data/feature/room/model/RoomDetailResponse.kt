package com.moim.data.feature.room.model

import kotlinx.serialization.Serializable

@Serializable
data class RoomDetailResponse(
    val roomInfo: RoomResponse,
    val role: String,
    val members: List<RoomMemberResponse>
)