package com.moim.data.feature.room.model

import com.moim.domain.model.RoomInfo
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

fun RoomResponse.toDomain() = RoomInfo(
    code = code,
    title = title,
    description = description,
    maxCount = maxCount,
    currentMemberCount = currentMemberCount
)