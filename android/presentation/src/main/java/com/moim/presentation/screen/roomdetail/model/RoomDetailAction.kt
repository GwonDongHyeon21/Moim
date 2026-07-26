package com.moim.presentation.screen.roomdetail.model

interface RoomDetailAction {

    data class RefreshRoomDetail(val roomId: Long) : RoomDetailAction

    data class NavigateToVote(val category: String) : RoomDetailAction

    data object NavigateBack : RoomDetailAction
}