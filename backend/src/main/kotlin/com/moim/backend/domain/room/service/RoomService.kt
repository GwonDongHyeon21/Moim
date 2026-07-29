package com.moim.backend.domain.room.service

import com.moim.backend.core.error.ErrorCode
import com.moim.backend.core.error.ErrorException
import com.moim.backend.domain.room.dto.*
import com.moim.backend.domain.room.entity.Room
import com.moim.backend.domain.room.entity.RoomMember
import com.moim.backend.domain.room.model.RoomFilterStatus
import com.moim.backend.domain.room.model.RoomRole
import com.moim.backend.domain.room.repository.RoomMemberRepository
import com.moim.backend.domain.room.repository.RoomRepository
import com.moim.backend.domain.room.service.Room.MAX_ROOM_COUNT
import com.moim.backend.domain.room.service.Room.ROOM_CODE_LENGTH
import com.moim.backend.domain.room.service.Room.charPool
import com.moim.backend.domain.user.repository.UserRepository
import com.moim.backend.domain.vote.model.Category
import com.moim.backend.domain.vote.repository.VoteRecordRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.time.LocalDateTime

private object Room {
    const val MAX_ROOM_COUNT = 10
    const val ROOM_CODE_LENGTH = 8
    val charPool = ('A'..'Z') + ('0'..'9')
}

@Service
@Transactional(readOnly = true)
class RoomService(
    private val roomRepository: RoomRepository,
    private val roomMemberRepository: RoomMemberRepository,
    private val voteRecordRepository: VoteRecordRepository,
    private val userRepository: UserRepository
) {

    private val secureRandom = SecureRandom()

    fun getMyRooms(userId: Long, page: Int, size: Int, status: String): List<RoomResponse> {
        val pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        val now = LocalDateTime.now()

        val rooms = if (status == RoomFilterStatus.ONGOING.name) {
            roomRepository.findOngoingRoomsByUserIdPaged(userId, now, pageable)
        } else {
            roomRepository.findClosedRoomsByUserIdPaged(userId, now, pageable)
        }

        return rooms.map { room ->
            val currentMemberCount = roomMemberRepository.countByRoomId(room.id!!)

            RoomResponse.from(room, currentMemberCount)
        }
    }


    fun getRoomDetail(userId: Long, roomId: Long): RoomDetailResponse {
        val roomMember = roomMemberRepository.findByRoomIdAndUserId(roomId, userId)
            ?: throw ErrorException(HttpStatus.FORBIDDEN, ErrorCode.ROOM_NOT_FOUND)

        val roomMembers = roomMemberRepository.findAllByRoomIdWithUser(roomId)
        val members = roomMembers.map { member ->
            RoomMemberResponse.from(
                user = member.user,
                role = member.role.name
            )
        }

        val myVoteRecords = voteRecordRepository.findAllByUserIdAndRoomId(userId, roomId)
        val myVotedCategories = myVoteRecords.map { it.candidate.category }.toSet()

        val categoryVoteStatus = Category.entries.map { category ->
            CategoryVoteStatusDto(
                category = category.name,
                isVoted = myVotedCategories.contains(category)
            )
        }

        return RoomDetailResponse(
            roomInfo = RoomResponse.from(roomMember.room, members.size),
            role = roomMember.role.name,
            members = members,
            categoryVoteStatus = categoryVoteStatus
        )
    }

    @Transactional
    fun createRoom(userId: Long, request: CreateRoomRequest): RoomResponse {
        val user = userRepository.findById(userId).orElseThrow {
            ErrorException(
                httpStatus = HttpStatus.NOT_FOUND,
                errorCode = ErrorCode.USER_NOT_FOUND
            )
        }

        val currentRoomCount = roomMemberRepository.countByUserId(userId)
        if (currentRoomCount >= MAX_ROOM_COUNT) {
            throw ErrorException(
                httpStatus = HttpStatus.FORBIDDEN,
                errorCode = ErrorCode.MAX_ROOM_LIMIT
            )
        }

        var randomRoomCode = generateRandomRoomCode()
        while (roomRepository.existsByCode(randomRoomCode)) {
            randomRoomCode = generateRandomRoomCode()
        }

        val newRoom = roomRepository.save(
            Room(
                code = randomRoomCode,
                title = request.title,
                description = request.description,
                maxCount = request.maxCount,
                deadline = request.deadline
            )
        )

        roomMemberRepository.save(
            RoomMember(
                room = newRoom,
                user = user,
                role = RoomRole.HOST
            )
        )

        return RoomResponse.from(
            room = newRoom,
            currentMemberCount = 1
        )
    }

    @Transactional
    fun joinRoom(userId: Long, request: JoinRoomRequest): RoomResponse {
        val user = userRepository.findById(userId).orElseThrow {
            ErrorException(
                httpStatus = HttpStatus.NOT_FOUND,
                errorCode = ErrorCode.USER_NOT_FOUND
            )
        }

        val room = roomRepository.findByCode(request.roomCode).orElseThrow {
            ErrorException(
                httpStatus = HttpStatus.NOT_FOUND,
                errorCode = ErrorCode.ROOM_NOT_FOUND
            )
        }

        if (LocalDateTime.now().isAfter(room.deadline)) {
            throw ErrorException(
                httpStatus = HttpStatus.FORBIDDEN,
                errorCode = ErrorCode.ROOM_DEADLINE_EXPIRED
            )
        }

        val roomId = room.id!!

        if (roomMemberRepository.existsByRoomIdAndUserId(roomId, userId)) {
            throw ErrorException(
                httpStatus = HttpStatus.CONFLICT,
                errorCode = ErrorCode.ALREADY_JOINED_ROOM
            )
        }

        val currentMemberCount = roomMemberRepository.countByRoomId(roomId)
        if (currentMemberCount >= room.maxCount) {
            throw ErrorException(
                httpStatus = HttpStatus.BAD_REQUEST,
                errorCode = ErrorCode.FULL_ROOM
            )
        }

        roomMemberRepository.save(
            RoomMember(
                room = room,
                user = user
            )
        )

        return RoomResponse.from(
            room = room,
            currentMemberCount = currentMemberCount + 1
        )
    }

    private fun generateRandomRoomCode(length: Int = ROOM_CODE_LENGTH): String {
        return (1..length)
            .map { secureRandom.nextInt(charPool.size).let { charPool[it] } }
            .joinToString("")
    }
}