package com.moim.backend.domain.room.repository

import com.moim.backend.domain.room.entity.RoomMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RoomMemberRepository : JpaRepository<RoomMember, Long> {

    fun countByRoomId(roomId: Long): Int

    fun existsByRoomIdAndUserId(roomId: Long, userId: Long): Boolean

    @Query("SELECT rm FROM RoomMember rm JOIN FETCH rm.user WHERE rm.room.id = :roomId")
    fun findAllByRoomIdWithUser(roomId: Long): List<RoomMember>

    fun findByRoomIdAndUserId(roomId: Long, userId: Long): RoomMember?
}