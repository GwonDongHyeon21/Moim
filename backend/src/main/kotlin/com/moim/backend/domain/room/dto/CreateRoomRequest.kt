package com.moim.backend.domain.room.dto

data class CreateRoomRequest(
    val title: String,
    val description: String?,
    val maxCount: Int
)