package com.moim.backend.domain.room.dto

import com.moim.backend.domain.user.entity.User

data class RoomMemberResponse(
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val role: String
) {
    companion object {
        fun from(user: User, role: String): RoomMemberResponse {
            return RoomMemberResponse(
                userId = user.id!!,
                nickname = user.nickname,
                profileImageUrl = user.profileImageUrl,
                role = role
            )
        }
    }
}