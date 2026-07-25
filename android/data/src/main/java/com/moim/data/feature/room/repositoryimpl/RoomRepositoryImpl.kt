package com.moim.data.feature.room.repositoryimpl

import com.moim.data.feature.room.datasource.RoomDataSource
import com.moim.data.feature.room.model.CreateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.toDomain
import com.moim.domain.feature.room.model.CreateRoomParams
import com.moim.domain.feature.room.model.RoomDetailInfo
import com.moim.domain.feature.room.model.RoomInfo
import com.moim.domain.feature.room.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource
) : RoomRepository {

    override suspend fun loadRooms(): Result<List<RoomInfo>> {
        return roomDataSource.loadRooms()
            .map { rooms -> rooms.map { it.toDomain() } }
    }

    override suspend fun loadRoomDetail(roomId: String): Result<RoomDetailInfo> {
        return roomDataSource.loadRoomDetail(roomId)
            .map { it.toDomain() }
    }

    override suspend fun createRoom(roomInfo: CreateRoomParams): Result<RoomInfo> {
        return roomDataSource.createRoom(
            CreateRoomRequest(
                title = roomInfo.title,
                description = roomInfo.description,
                maxCount = roomInfo.maxCount,
                deadline = roomInfo.deadline
            )
        ).map { it.toDomain() }
    }

    override suspend fun joinRoom(roomCode: String): Result<RoomInfo> {
        return roomDataSource.joinRoom(JoinRoomRequest(roomCode))
            .map { it.toDomain() }
    }
}