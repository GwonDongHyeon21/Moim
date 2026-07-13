package com.moim.presentation.screen.roomdetail.model

import com.moim.domain.model.RoomMemberInfo

data class RoomMemberInfoUiModel(
    val userId: Long? = null,
    val nickname: String = "",
    val profileImageUrl: String? = null,
    val role: String = ""
)

fun RoomMemberInfo.toUiModel() = RoomMemberInfoUiModel(
    userId = userId,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    role = role
)