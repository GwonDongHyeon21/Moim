package com.moim.domain.feature.room.model

data class CreateUpdateRoomParams(
    val title: String,
    val description: String,
    val maxCount: Int = 100,
    val deadline: String
)