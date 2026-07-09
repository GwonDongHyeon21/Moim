package com.moim.backend.domain.room.controller

import com.moim.backend.core.response.ApiResponse
import com.moim.backend.domain.room.dto.CreateRoomRequest
import com.moim.backend.domain.room.dto.JoinRoomRequest
import com.moim.backend.domain.room.dto.RoomDetailResponse
import com.moim.backend.domain.room.dto.RoomResponse
import com.moim.backend.domain.room.service.RoomService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/rooms")
class RoomController(
    private val roomService: RoomService,
) {

    @GetMapping
    fun getMyRooms(
        @AuthenticationPrincipal userId: Long
    ): ApiResponse<List<RoomResponse>> {
        val response = roomService.getMyRooms(userId)

        return ApiResponse.success(response)
    }

    @GetMapping("/{roomId}")
    fun getRoomDetail(
        @AuthenticationPrincipal userId: Long,
        @PathVariable roomId: Long
    ): ApiResponse<RoomDetailResponse> {
        val response = roomService.getRoomDetail(userId, roomId)

        return ApiResponse.success(response)
    }

    @PostMapping("/create")
    fun createRoom(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: CreateRoomRequest
    ): ApiResponse<RoomResponse> {
        val response = roomService.createRoom(userId, request)

        return ApiResponse.success(response)
    }

    @PostMapping("/join")
    fun joinRoom(
        @AuthenticationPrincipal userId: Long,
        @RequestBody request: JoinRoomRequest
    ): ApiResponse<RoomResponse> {
        val response = roomService.joinRoom(userId, request)

        return ApiResponse.success(response)
    }
}