package com.moim.presentation.screen.login.model

sealed interface LoginEvent {

    data object NavigateToHome : LoginEvent

    data class ShowSnackBar(val message: Int) : LoginEvent
}