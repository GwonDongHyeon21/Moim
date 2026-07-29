package com.moim.presentation.screen.home.model

interface HomeEvent {

    data class NavigateToRoomDetail(val roomId: Long) : HomeEvent

    data object RefreshRoom : HomeEvent
}