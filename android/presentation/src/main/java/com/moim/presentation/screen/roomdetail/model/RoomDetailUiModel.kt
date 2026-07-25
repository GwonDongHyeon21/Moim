package com.moim.presentation.screen.roomdetail.model

import com.moim.domain.feature.room.model.RoomDetailInfo
import com.moim.presentation.model.RoomInfoUiModel
import com.moim.presentation.model.toUiModel

data class RoomDetailUiModel(
    val roomInfo: RoomInfoUiModel = RoomInfoUiModel(),
    val role: String = "",
    val members: List<RoomMemberInfoUiModel> = emptyList()
)

fun RoomDetailInfo.toUiModel() = RoomDetailUiModel(
    roomInfo = roomInfo.toUiModel(),
    role = role,
    members = members.map { it.toUiModel() }
)