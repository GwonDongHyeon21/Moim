package com.moim.backend.domain.room.repository

import com.moim.backend.domain.room.entity.Room
import io.lettuce.core.dynamic.annotation.Param
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface RoomRepository : JpaRepository<Room, Long> {

    fun findByCode(code: String): Optional<Room>

    fun existsByCode(code: String): Boolean

    @Query("SELECT COUNT(r) FROM Room r JOIN RoomMember rm ON r.id = rm.room.id WHERE rm.user.id = :userId")
    fun countByUserId(userId: Long): Int

    @Query("SELECT r FROM Room r JOIN RoomMember rm ON r.id = rm.room.id WHERE rm.user.id = :userId AND r.deadline > :now")
    fun findOngoingRoomsByUserIdPaged(
        @Param("userId") userId: Long,
        @Param("now") now: LocalDateTime,
        pageable: Pageable
    ): List<Room>

    @Query("SELECT r FROM Room r JOIN RoomMember rm ON r.id = rm.room.id WHERE rm.user.id = :userId AND r.deadline <= :now")
    fun findClosedRoomsByUserIdPaged(
        @Param("userId") userId: Long,
        @Param("now") now: LocalDateTime,
        pageable: Pageable
    ): List<Room>
}