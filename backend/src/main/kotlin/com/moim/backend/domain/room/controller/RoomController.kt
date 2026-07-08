package com.moim.backend.domain.room.controller

import com.moim.backend.core.response.ApiResponse
import com.moim.backend.domain.room.dto.CreateRoomRequest
import com.moim.backend.domain.room.dto.JoinRoomRequest
import com.moim.backend.domain.room.dto.RoomResponse
import com.moim.backend.domain.room.service.RoomService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rooms")
class RoomController(
    private val roomService: RoomService,
) {

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