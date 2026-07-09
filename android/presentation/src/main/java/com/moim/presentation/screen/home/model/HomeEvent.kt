package com.moim.presentation.screen.home.model

interface HomeEvent {

    data class NavigateToRoomDetail(val roomCode: String) : HomeEvent

    data class ShowSnackBar(val message: Int) : HomeEvent
}