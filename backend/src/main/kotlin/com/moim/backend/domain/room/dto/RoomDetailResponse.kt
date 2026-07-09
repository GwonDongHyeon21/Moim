package com.moim.backend.domain.room.dto

import com.moim.backend.domain.room.entity.RoomMember

data class RoomDetailResponse(
    val roomInfo: RoomResponse,
    val role: String,
    val members: List<RoomMember>
)