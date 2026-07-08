package com.moim.data.feature.room.model

import kotlinx.serialization.Serializable

@Serializable
data class RoomResponse(
    val id: Long?,
    val code: String,
    val title: String,
    val description: String?,
    val maxCount: Int,
    val currentMemberCount: Int
)