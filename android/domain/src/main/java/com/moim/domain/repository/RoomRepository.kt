package com.moim.domain.repository

import com.moim.domain.model.RoomInfo

interface RoomRepository {

    suspend fun createRoom(title: String, description: String): Result<RoomInfo>

    suspend fun joinRoom(roomCode: String): Result<RoomInfo>
}