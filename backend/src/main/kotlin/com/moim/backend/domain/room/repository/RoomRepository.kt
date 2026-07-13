package com.moim.backend.domain.room.repository

import com.moim.backend.domain.room.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface RoomRepository : JpaRepository<Room, Long> {

    fun findByCode(code: String): Optional<Room>

    fun existsByCode(code: String): Boolean

    @Query("SELECT r FROM Room r JOIN RoomMember rm ON r.id = rm.room.id WHERE rm.user.id = :userId")
    fun findJoinedRoomsByUserId(userId: Long): List<Room>
}