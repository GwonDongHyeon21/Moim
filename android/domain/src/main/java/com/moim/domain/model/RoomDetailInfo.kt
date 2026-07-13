package com.moim.domain.model

data class RoomDetailInfo(
    val roomInfo: RoomInfo,
    val role: String,
    val members: List<RoomMemberInfo>
)