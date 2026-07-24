package com.moim.presentation.screen.roomdetail.model

interface RoomDetailAction {

    data class RefreshRoomDetail(val roomId: Long) : RoomDetailAction

    data object NavigateBack : RoomDetailAction
}