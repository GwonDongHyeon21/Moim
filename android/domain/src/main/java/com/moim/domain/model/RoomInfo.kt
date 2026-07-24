package com.moim.domain.model

data class RoomInfo(
    val id: Long,
    val code: String,
    val title: String,
    val description: String?,
    val maxCount: Int,
    val currentMemberCount: Int,
    val deadline: String
)