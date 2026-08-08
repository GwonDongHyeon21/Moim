package com.moim.data.feature.room.model

import com.moim.domain.feature.room.model.RoomInfo
import kotlinx.serialization.Serializable

@Serializable
data class RoomResponse(
    val id: Long?,
    val code: String,
    val title: String,
    val description: String?,
    val maxCount: Int,
    val currentMemberCount: Int,
    val deadline: String,
    val isHost: Boolean
)

fun RoomResponse.toDomain() = RoomInfo(
    id = id!!,
    code = code,
    title = title,
    description = description,
    maxCount = maxCount,
    currentMemberCount = currentMemberCount,
    deadline = deadline,
    isHost = isHost
)