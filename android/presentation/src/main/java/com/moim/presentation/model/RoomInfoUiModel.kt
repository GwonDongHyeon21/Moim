package com.moim.presentation.model

import com.moim.domain.feature.room.model.RoomInfo

data class RoomInfoUiModel(
    val id: Long? = null,
    val code: String = "",
    val title: String = "",
    val description: String? = "",
    val maxCount: Int = 0,
    val currentMemberCount: Int = 0,
    val deadline: String = ""
)

fun RoomInfo.toUiModel() = RoomInfoUiModel(
    id = id,
    code = code,
    title = title,
    description = description,
    maxCount = maxCount,
    currentMemberCount = currentMemberCount,
    deadline = deadline
)