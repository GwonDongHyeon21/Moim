package com.moim.data.feature.room.datasource

import com.moim.data.common.model.ApiResponse
import com.moim.data.feature.room.model.CreateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.RoomDetailResponse
import com.moim.data.feature.room.model.RoomResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RoomService {

    @GET("api/v1/rooms")
    suspend fun getMyRooms(): ApiResponse<List<RoomResponse>>

    @GET("api/v1/rooms/{roomId}")
    suspend fun getRoomDetail(
        @Path("roomId") roomId: Long
    ): ApiResponse<RoomDetailResponse>

    @POST("api/v1/rooms/create")
    suspend fun createRoom(
        @Body request: CreateRoomRequest
    ): ApiResponse<RoomResponse>

    @POST("api/v1/rooms/join")
    suspend fun joinRoom(
        @Body request: JoinRoomRequest
    ): ApiResponse<RoomResponse>
}