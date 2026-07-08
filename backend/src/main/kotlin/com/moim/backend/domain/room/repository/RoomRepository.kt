package com.moim.backend.domain.room.repository

import com.moim.backend.domain.room.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoomRepository : JpaRepository<Room, Long> {
    fun findByCode(code: String): Optional<Room>
    fun existsByCode(code: String): Boolean
}