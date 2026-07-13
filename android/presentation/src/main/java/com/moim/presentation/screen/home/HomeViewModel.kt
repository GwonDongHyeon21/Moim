package com.moim.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moim.domain.model.CreateRoomParams
import com.moim.domain.repository.RoomRepository
import com.moim.presentation.model.toUiModel
import com.moim.presentation.screen.home.model.HomeAction
import com.moim.presentation.screen.home.model.HomeEvent
import com.moim.presentation.screen.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeEvent>(BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadRooms()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ClickRoom -> {
                _uiEvent.trySend(HomeEvent.NavigateToRoomDetail(action.roomId))
            }

            is HomeAction.CreateRoom -> {
                createRoom(action.roomInfo)
            }

            is HomeAction.JoinRoom -> {
                joinRoom(action.roomCode)
            }
        }
    }

    fun loadRooms() {
        viewModelScope.launch {
            roomRepository.loadRooms()
                .onSuccess { data ->
                    _uiState.update { it.copy(rooms = data.map { room -> room.toUiModel() }) }
                }.onFailure {
                    // 아직
                }
        }
    }

    fun createRoom(roomInfo: CreateRoomParams) {
        viewModelScope.launch {
            roomRepository.createRoom(roomInfo)
                .onSuccess {
                    loadRooms()
                }.onFailure {
                    // 아직
                }
        }
    }

    fun joinRoom(roomCode: String) {
        viewModelScope.launch {
            roomRepository.joinRoom(roomCode)
                .onSuccess { data ->
                    _uiEvent.trySend(HomeEvent.NavigateToRoomDetail(data.id))
                    loadRooms()
                }.onFailure {
                    // 아직
                }
        }
    }
}