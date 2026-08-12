package com.moim.backend.domain.room.repository

import com.moim.backend.domain.room.entity.RoomMember
import com.moim.backend.domain.room.model.RoomMemberCount
import io.lettuce.core.dynamic.annotation.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RoomMemberRepository : JpaRepository<RoomMember, Long> {

    @Query("SELECT rm.room.id AS roomId, COUNT(rm) AS count FROM RoomMember rm WHERE rm.room.id IN :roomIds GROUP BY rm.room.id")
    fun countByRoomIds(@Param("roomIds") roomIds: List<Long>): List<RoomMemberCount>

    fun findByUserIdAndRoomIdIn(userId: Long, roomIds: List<Long>): List<RoomMember>

    fun countByRoomId(roomId: Long): Int

    fun existsByRoomIdAndUserId(roomId: Long, userId: Long): Boolean

    @Query("SELECT rm FROM RoomMember rm JOIN FETCH rm.user WHERE rm.room.id = :roomId")
    fun findAllByRoomIdWithUser(roomId: Long): List<RoomMember>

    fun findByRoomIdAndUserId(roomId: Long, userId: Long): RoomMember?
}