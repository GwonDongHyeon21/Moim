package com.moim.presentation.screen.home.model

interface HomeEvent {

    data class NavigateToRoomDetail(val roomId: Long) : HomeEvent

    data object NavigateToLogin : HomeEvent

    data class ShowSnackBar(val message: Int) : HomeEvent
}