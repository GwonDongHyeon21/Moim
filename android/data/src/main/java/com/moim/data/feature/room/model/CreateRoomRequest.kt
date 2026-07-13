package com.moim.data.feature.room.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateRoomRequest(
    val title: String,
    val description: String?,
    val maxCount: Int
)