package com.moim.backend.domain.room.model

interface RoomMemberCount {
    fun getRoomId(): Long
    fun getCount(): Int
}