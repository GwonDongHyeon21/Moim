package com.moim.data.feature.room.datasource

import com.moim.data.common.model.ApiResponse
import com.moim.data.feature.room.model.CreateUpdateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.RoomDetailResponse
import com.moim.data.feature.room.model.RoomResponse
import com.moim.domain.feature.room.model.CreateUpdateRoomParams
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RoomService {

    @GET("/api/v1/rooms")
    suspend fun getRoomsPaging(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("status") status: String
    ): ApiResponse<List<RoomResponse>>

    @GET("api/v1/rooms/{roomId}")
    suspend fun getRoomDetail(
        @Path("roomId") roomId: Long
    ): ApiResponse<RoomDetailResponse>

    @POST("api/v1/rooms/create")
    suspend fun createRoom(
        @Body request: CreateUpdateRoomRequest
    ): ApiResponse<RoomResponse>

    @POST("api/v1/rooms/join")
    suspend fun joinRoom(
        @Body request: JoinRoomRequest
    ): ApiResponse<RoomResponse>

    @PUT("api/v1/rooms/{roomId}")
    suspend fun updateRoom(
        @Path("roomId") roomId: Long,
        @Body request: CreateUpdateRoomRequest
    ): ApiResponse<RoomResponse>

    @DELETE("api/v1/rooms/{roomId}")
    suspend fun deleteRoom(
        @Path("roomId") roomId: Long
    ): ApiResponse<Long>
}