package com.moim.data.feature.room.model

import kotlinx.serialization.Serializable

@Serializable
data class JoinRoomRequest(
    val roomCode: String
)