package com.moim.domain.feature.room.repository

import com.moim.domain.feature.room.model.CreateRoomParams
import com.moim.domain.feature.room.model.RoomDetailInfo
import com.moim.domain.feature.room.model.RoomInfo

interface RoomRepository {

    suspend fun loadRooms(): Result<List<RoomInfo>>

    suspend fun loadRoomDetail(roomId: String): Result<RoomDetailInfo>

    suspend fun createRoom(roomInfo: CreateRoomParams): Result<RoomInfo>

    suspend fun joinRoom(roomCode: String): Result<RoomInfo>
}