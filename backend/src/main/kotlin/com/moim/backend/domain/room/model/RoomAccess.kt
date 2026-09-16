package com.moim.backend.domain.room.model

import com.moim.backend.domain.room.entity.Room
import com.moim.backend.domain.room.entity.RoomMember

data class RoomAccess(
    val room: Room,
    val member: RoomMember
)