package com.moim.data.feature.room.datasource

import com.moim.data.feature.room.model.CreateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.RoomDetailResponse
import com.moim.data.feature.room.model.RoomResponse

interface RoomDataSource {

    suspend fun loadRooms(): Result<List<RoomResponse>>

    suspend fun loadRoomDetail(roomId: String): Result<RoomDetailResponse>

    suspend fun createRoom(request: CreateRoomRequest): Result<RoomResponse>

    suspend fun joinRoom(request: JoinRoomRequest): Result<RoomResponse>
}