package com.moim.backend.domain.room.service

import com.moim.backend.core.error.ErrorCode
import com.moim.backend.core.error.ErrorException
import com.moim.backend.domain.room.entity.Room
import com.moim.backend.domain.room.model.RoomAccess
import com.moim.backend.domain.room.model.RoomRole
import com.moim.backend.domain.room.repository.RoomMemberRepository
import com.moim.backend.domain.room.repository.RoomRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RoomAccessValidator(
    private val roomRepository: RoomRepository,
    private val roomMemberRepository: RoomMemberRepository
) {

    fun getRoom(roomId: Long): Room {
        val room = roomRepository.findById(roomId).orElseThrow {
            ErrorException(HttpStatus.NOT_FOUND, ErrorCode.ROOM_NOT_FOUND)
        }

        return room
    }

    fun getRoomAsMember(userId: Long, roomId: Long): RoomAccess {
        val room = getRoom(roomId)
        val member = roomMemberRepository.findByRoomIdAndUserId(roomId, userId)
            ?: throw ErrorException(HttpStatus.FORBIDDEN, ErrorCode.ROOM_NOT_FOUND)

        return RoomAccess(room, member)
    }

    fun getRoomAsHost(userId: Long, roomId: Long): RoomAccess {
        val roomAccess = getRoomAsMember(userId, roomId)
        if (roomAccess.member.role != RoomRole.HOST) {
            throw ErrorException(HttpStatus.FORBIDDEN, ErrorCode.NOT_ROOM_PERMISSION)
        }

        return roomAccess
    }

    fun getActiveRoomAsMember(userId: Long, roomId: Long): RoomAccess {
        val roomAccess = getRoomAsMember(userId, roomId)
        if (LocalDateTime.now().isAfter(roomAccess.room.deadline)) {
            throw ErrorException(HttpStatus.FORBIDDEN, ErrorCode.ROOM_DEADLINE_EXPIRED)
        }

        return roomAccess
    }
}