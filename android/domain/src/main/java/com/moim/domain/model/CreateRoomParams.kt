package com.moim.domain.model

data class CreateRoomParams(
    val title: String,
    val description: String,
    val maxCount: Int = 100
)