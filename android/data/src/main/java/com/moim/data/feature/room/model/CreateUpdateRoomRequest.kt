package com.moim.data.feature.room.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateUpdateRoomRequest(
    val title: String,
    val description: String?,
    val maxCount: Int,
    val deadline: String
)