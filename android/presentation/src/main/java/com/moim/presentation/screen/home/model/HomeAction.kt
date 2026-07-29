package com.moim.presentation.screen.home.model

import com.moim.domain.feature.room.model.CreateRoomParams
import java.time.LocalDateTime

interface HomeAction {

    data class ClickRoom(val roomId: Long) : HomeAction

    data class ClickDialog(val isExpanded: Boolean, val roomOption: String) : HomeAction

    data class OnTitleChanged(val title: String) : HomeAction

    data class OnDescriptionChanged(val description: String) : HomeAction

    data class OnDateTimeSelected(val selectedDateTime: LocalDateTime) : HomeAction

    data class OnRoomFilterStatusSelected(val roomFilterStatus: RoomFilterStatus) : HomeAction

    data class CreateRoom(val roomInfo: CreateRoomParams) : HomeAction

    data class JoinRoom(val roomCode: String) : HomeAction

    data object Logout : HomeAction
}