package com.moim.data.feature.room.datasource

import com.moim.data.feature.room.model.CreateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.RoomResponse

interface RoomDataSource {

    suspend fun createRoom(request: CreateRoomRequest): Result<RoomResponse>

    suspend fun joinRoom(request: JoinRoomRequest): Result<RoomResponse>
}