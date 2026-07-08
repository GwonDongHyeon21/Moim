package com.moim.data.feature.room.datasource

import com.moim.data.common.model.ApiResponse
import com.moim.data.feature.room.model.CreateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.RoomResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RoomService {

    @POST("api/v1/rooms/create")
    suspend fun createRoom(
        @Body request: CreateRoomRequest
    ): ApiResponse<RoomResponse>

    @POST("api/v1/rooms/join")
    suspend fun joinRoom(
        @Body request: JoinRoomRequest
    ): ApiResponse<RoomResponse>
}