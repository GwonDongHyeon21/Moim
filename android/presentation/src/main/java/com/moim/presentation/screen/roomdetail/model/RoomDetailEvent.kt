package com.moim.presentation.screen.roomdetail.model

interface RoomDetailEvent {

    data class NavigateToVote(
        val roomId: Long,
        val category: String
    ) : RoomDetailEvent

    data class NavigateToCandidateCreate(val roomId: Long) : RoomDetailEvent

    data class NavigateToCandidateUpdate(val category: String) : RoomDetailEvent

    data object NavigateBack : RoomDetailEvent

    data object NavigateBackRefresh : RoomDetailEvent
}