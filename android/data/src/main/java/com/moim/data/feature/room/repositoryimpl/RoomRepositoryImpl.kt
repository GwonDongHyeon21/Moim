package com.moim.data.feature.room.repositoryimpl

import com.moim.data.feature.room.datasource.RoomDataSource
import com.moim.data.feature.room.model.CreateRoomRequest
import com.moim.data.feature.room.model.JoinRoomRequest
import com.moim.data.feature.room.model.toDomain
import com.moim.domain.model.RoomInfo
import com.moim.domain.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource
) : RoomRepository {
    override suspend fun createRoom(
        title: String,
        description: String
    ): Result<RoomInfo> {
        return roomDataSource.createRoom(
            CreateRoomRequest(
                title = title,
                description = description,
                maxCount = 10
            )
        ).map { it.toDomain() }
    }

    override suspend fun joinRoom(roomCode: String): Result<RoomInfo> {
        return roomDataSource.joinRoom(JoinRoomRequest(roomCode))
            .map { it.toDomain() }
    }
}