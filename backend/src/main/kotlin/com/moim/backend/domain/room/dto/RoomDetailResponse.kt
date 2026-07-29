package com.moim.backend.domain.room.dto

data class RoomDetailResponse(
    val roomInfo: RoomResponse,
    val role: String,
    val members: List<RoomMemberResponse>,
    val categoryVoteStatus: List<CategoryVoteStatusDto>
)