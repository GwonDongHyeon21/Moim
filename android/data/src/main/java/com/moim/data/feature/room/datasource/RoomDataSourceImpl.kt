package com.moim.data.feature.room.datasource

import com.moim.data.common.network.apiCall
import com.moim.data.feature.room.model.CreateUpdateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.RoomDetailResponse
import com.moim.data.feature.room.model.RoomResponse
import javax.inject.Inject

class RoomDataSourceImpl @Inject constructor(
    private val roomService: RoomService
) : RoomDataSource {

    override suspend fun getRoomsPaging(
        page: Int,
        size: Int,
        status: String
    ): Result<List<RoomResponse>> {
        return apiCall { roomService.getRoomsPaging(page, size, status) }
    }

    override suspend fun loadRoomDetail(roomId: Long): Result<RoomDetailResponse> {
        return apiCall { roomService.getRoomDetail(roomId) }
    }

    override suspend fun createRoom(request: CreateUpdateRoomRequest): Result<RoomResponse> {
        return apiCall { roomService.createRoom(request) }
    }

    override suspend fun joinRoom(request: JoinRoomRequest): Result<RoomResponse> {
        return apiCall { roomService.joinRoom(request) }
    }

    override suspend fun updateRoom(
        roomId: Long,
        request: CreateUpdateRoomRequest
    ): Result<RoomResponse> {
        return apiCall { roomService.updateRoom(roomId, request) }
    }

    override suspend fun deleteRoom(roomId: Long): Result<Long> {
        return apiCall { roomService.deleteRoom(roomId) }
    }
}