package com.moim.presentation.screen.home.model

import com.moim.domain.model.CreateRoomParams

interface HomeAction {

    data class ClickRoom(val roomId: Long) : HomeAction

    data class CreateRoom(val roomInfo: CreateRoomParams) : HomeAction

    data class JoinRoom(val roomCode: String) : HomeAction
}