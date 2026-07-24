package com.moim.backend.domain.room.dto

import java.time.LocalDateTime

data class CreateRoomRequest(
    val title: String,
    val description: String?,
    val maxCount: Int,
    val deadline: LocalDateTime
)