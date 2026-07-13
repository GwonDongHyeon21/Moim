package com.moim.backend.domain.room.dto

import com.moim.backend.domain.room.entity.Room

data class RoomResponse(
    val id: Long?,
    val code: String,
    val title: String,
    val description: String?,
    val maxCount: Int,
    val currentMemberCount: Int
) {
    companion object {
        fun from(room: Room, currentMemberCount: Int): RoomResponse {
            return RoomResponse(
                id = room.id,
                code = room.code,
                title = room.title,
                description = room.description,
                maxCount = room.maxCount,
                currentMemberCount = currentMemberCount
            )
        }
    }
}