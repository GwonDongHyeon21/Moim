package com.moim.presentation.screen.roomdetail.model

interface RoomDetailAction {

    data class LoadRoomDetail(val roomId: Long) : RoomDetailAction

    data object NavigateBack : RoomDetailAction
}