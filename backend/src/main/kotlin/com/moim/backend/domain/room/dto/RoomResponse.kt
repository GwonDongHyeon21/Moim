package com.moim.backend.domain.room.dto

import com.moim.backend.domain.room.entity.Room
import java.time.LocalDateTime

data class RoomResponse(
    val id: Long?,
    val code: String,
    val title: String,
    val description: String?,
    val maxCount: Int,
    val currentMemberCount: Int,
    val deadline: LocalDateTime,
    val isHost: Boolean
) {
    companion object {
        fun from(room: Room, currentMemberCount: Int, isHost: Boolean): RoomResponse {
            return RoomResponse(
                id = room.id,
                code = room.code,
                title = room.title,
                description = room.description,
                maxCount = room.maxCount,
                currentMemberCount = currentMemberCount,
                deadline = room.deadline,
                isHost = isHost
            )
        }
    }
}