package com.moim.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.moim.domain.model.CreateRoomParams
import com.moim.domain.repository.RoomRepository
import com.moim.domain.repository.UserRepository
import com.moim.presentation.model.toUiModel
import com.moim.presentation.screen.home.model.HomeAction
import com.moim.presentation.screen.home.model.HomeEvent
import com.moim.presentation.screen.home.model.HomeUiState
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeEvent>(BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            try {
                loadRooms()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ClickRoom -> _uiEvent.trySend(HomeEvent.NavigateToRoomDetail(action.roomId))

            is HomeAction.CreateRoom -> createRoom(action.roomInfo)

            is HomeAction.JoinRoom -> joinRoom(action.roomCode)

            HomeAction.RefreshHome -> refreshRooms()

            HomeAction.Logout -> logout()
        }
    }

    suspend fun loadRooms() {
        roomRepository.loadRooms()
            .onSuccess { data ->
                _uiState.update { it.copy(rooms = data.map { room -> room.toUiModel() }) }
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                Timber.e(exception)
                FirebaseCrashlytics.getInstance().recordException(exception)
            }
    }

    fun refreshRooms() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isRefreshing = true) }
                loadRooms()
            } finally {
                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }

    fun createRoom(roomInfo: CreateRoomParams) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                roomRepository.createRoom(roomInfo)
                    .onSuccess { data ->
                        _uiEvent.trySend(HomeEvent.NavigateToRoomDetail(data.id))
                        loadRooms()
                    }.onFailure { exception ->
                        snackBarManager.show(SnackBarEvent.DATA_SAVE_FAILED)

                        Timber.e(exception)
                        FirebaseCrashlytics.getInstance().recordException(exception)
                    }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun joinRoom(roomCode: String) {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                roomRepository.joinRoom(roomCode)
                    .onSuccess { data ->
                        _uiEvent.trySend(HomeEvent.NavigateToRoomDetail(data.id))
                        loadRooms()
                    }.onFailure { exception ->
                        snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                        Timber.e(exception)
                        FirebaseCrashlytics.getInstance().recordException(exception)
                    }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun logout() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                userRepository.logout()
                    .onSuccess {
                        _uiEvent.trySend(HomeEvent.NavigateToLogin)
                    }.onFailure {
                        // 아직
                    }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}