package com.moim.backend.domain.room.repository

import com.moim.backend.domain.room.entity.RoomMember
import org.springframework.data.jpa.repository.JpaRepository

interface RoomMemberRepository : JpaRepository<RoomMember, Long> {
    fun countByRoomId(roomId: Long): Int
    fun existsByRoomIdAndUserId(roomId: Long, userId: Long): Boolean
    fun countByUserId(userId: Long): Int
}