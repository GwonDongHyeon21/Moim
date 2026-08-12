package com.moim.presentation.screen.roomdetail.model

import java.time.LocalDateTime

interface RoomDetailAction {

    data object LoadRoomDetail : RoomDetailAction

    data object RefreshRoomDetail : RoomDetailAction

    data object DeleteRoom : RoomDetailAction

    data class OnTitleChanged(val title: String) : RoomDetailAction

    data class OnDescriptionChanged(val description: String) : RoomDetailAction

    data class OnDateTimeSelected(val selectedDateTime: LocalDateTime) : RoomDetailAction

    data class UpdateRoom(val deadline: String) : RoomDetailAction

    data class NavigateToVote(val category: String) : RoomDetailAction

    data object NavigateToCandidateCreate : RoomDetailAction

    data class NavigateToCandidateUpdate(val category: String) : RoomDetailAction

    data object NavigateBack : RoomDetailAction
}