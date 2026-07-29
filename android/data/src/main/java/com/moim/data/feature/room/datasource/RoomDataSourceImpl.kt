package com.moim.data.feature.room.datasource

import com.moim.data.common.network.apiCall
import com.moim.data.feature.room.model.CreateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.RoomDetailResponse
import com.moim.data.feature.room.model.RoomResponse
import javax.inject.Inject

class RoomDataSourceImpl @Inject constructor(
    private val roomService: RoomService
) : RoomDataSource {

    override suspend fun loadRooms(): Result<List<RoomResponse>> {
        return apiCall { roomService.getMyRooms() }
    }

    override suspend fun loadRoomDetail(roomId: Long): Result<RoomDetailResponse> {
        return apiCall { roomService.getRoomDetail(roomId) }
    }

    override suspend fun createRoom(request: CreateRoomRequest): Result<RoomResponse> {
        return apiCall { roomService.createRoom(request) }
    }

    override suspend fun joinRoom(request: JoinRoomRequest): Result<RoomResponse> {
        return apiCall { roomService.joinRoom(request) }
    }
}