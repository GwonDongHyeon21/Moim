package com.moim.domain.model

data class RoomInfo(
    val code: String,
    val title: String,
    val description: String?,
    val maxCount: Int,
    val currentMemberCount: Int
)