package com.moim.domain.feature.room.model

data class RoomDetailInfo(
    val roomInfo: RoomInfo,
    val role: String,
    val members: List<RoomMemberInfo>
)