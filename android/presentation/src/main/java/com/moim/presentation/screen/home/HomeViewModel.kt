package com.moim.presentation.screen.home

import com.moim.domain.model.CreateRoomParams
import com.moim.domain.repository.RoomRepository
import com.moim.domain.repository.UserRepository
import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.model.toUiModel
import com.moim.presentation.screen.home.model.HomeAction
import com.moim.presentation.screen.home.model.HomeEvent
import com.moim.presentation.screen.home.model.HomeUiState
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()) {

    override fun checkLoading() = uiState.value.isLoading
    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    init {
        doAction { loadRooms() }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ClickRoom -> sendEvent(HomeEvent.NavigateToRoomDetail(action.roomId))

            is HomeAction.ClickDialog ->
                updateState { copy(isExpanded = action.isExpanded, roomOption = action.roomOption) }

            is HomeAction.OnTitleChanged -> updateState { copy(title = action.title) }

            is HomeAction.OnDescriptionChanged -> updateState { copy(description = action.description) }

            is HomeAction.OnDateTimeSelected -> updateState { copy(selectedDateTime = action.selectedDateTime) }

            is HomeAction.OnRoomFilterStatusSelected -> updateState { copy(roomFilterStatus = action.roomFilterStatus) }

            is HomeAction.CreateRoom -> createRoom(action.roomInfo)

            is HomeAction.JoinRoom -> joinRoom(action.roomCode)

            HomeAction.RefreshHome -> refreshRooms()

            HomeAction.Logout -> logout()
        }
    }

    private suspend fun loadRooms() {
        roomRepository.loadRooms()
            .onSuccess { data ->
                updateState { copy(rooms = data.map { it.toUiModel() }) }
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                Timber.e(exception)
            }
    }

    private fun createRoom(roomInfo: CreateRoomParams) = doAction {
        roomRepository.createRoom(roomInfo)
            .onSuccess { data ->
                sendEvent(HomeEvent.NavigateToRoomDetail(data.id))
                updateState {
                    copy(
                        title = "",
                        description = "",
                        isExpanded = false,
                        roomOption = ""
                    )
                }
                loadRooms()
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.DATA_SAVE_FAILED)

                Timber.e(exception)
            }
    }

    private fun joinRoom(roomCode: String) = doAction {
        roomRepository.joinRoom(roomCode)
            .onSuccess { data ->
                sendEvent(HomeEvent.NavigateToRoomDetail(data.id))
                updateState {
                    copy(
                        title = "",
                        description = "",
                        isExpanded = false,
                        roomOption = ""
                    )
                }
                loadRooms()
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                Timber.e(exception)
            }
    }

    private fun refreshRooms() = doAction(
        customCheck = { uiState.value.isRefreshing },
        customUpdate = { updateState { copy(isRefreshing = it) } }
    ) {
        loadRooms()
    }

    private fun logout() = doAction {
        userRepository.logout()
    }
}