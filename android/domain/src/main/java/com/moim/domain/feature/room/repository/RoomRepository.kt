package com.moim.domain.feature.room.repository

import androidx.paging.PagingData
import com.moim.domain.feature.room.model.CreateRoomParams
import com.moim.domain.feature.room.model.RoomDetailInfo
import com.moim.domain.feature.room.model.RoomInfo
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    fun getRoomsPaging(status: String): Flow<PagingData<RoomInfo>>

    suspend fun loadRoomDetail(roomId: Long): Result<RoomDetailInfo>

    suspend fun createRoom(roomInfo: CreateRoomParams): Result<RoomInfo>

    suspend fun joinRoom(roomCode: String): Result<RoomInfo>
}