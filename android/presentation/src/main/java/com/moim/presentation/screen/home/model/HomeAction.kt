package com.moim.presentation.screen.home.model

interface HomeAction {

    data class ClickRoom(val roomId: Long) : HomeAction
}