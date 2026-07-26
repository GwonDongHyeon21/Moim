package com.moim.presentation.screen.roomdetail.model

interface RoomDetailEvent {

    data class NavigateToVote(
        val roomId: Long,
        val category: String
    ) : RoomDetailEvent

    data object NavigateBack : RoomDetailEvent
}