package com.moim.data.feature.room.datasource

import com.moim.data.feature.room.model.CreateUpdateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.RoomDetailResponse
import com.moim.data.feature.room.model.RoomResponse

interface RoomDataSource {

    suspend fun getRoomsPaging(page: Int, size: Int, status: String): Result<List<RoomResponse>>

    suspend fun loadRoomDetail(roomId: Long): Result<RoomDetailResponse>

    suspend fun createRoom(request: CreateUpdateRoomRequest): Result<RoomResponse>

    suspend fun joinRoom(request: JoinRoomRequest): Result<RoomResponse>

    suspend fun updateRoom(roomId: Long, request: CreateUpdateRoomRequest): Result<RoomResponse>

    suspend fun deleteRoom(roomId: Long): Result<Long>
}