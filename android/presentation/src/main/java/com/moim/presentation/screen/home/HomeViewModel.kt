package com.moim.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.moim.domain.feature.room.model.CreateRoomParams
import com.moim.domain.feature.room.repository.RoomRepository
import com.moim.domain.feature.user.repository.UserRepository
import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.model.toUiModel
import com.moim.presentation.screen.home.model.HomeAction
import com.moim.presentation.screen.home.model.HomeEvent
import com.moim.presentation.screen.home.model.HomeUiState
import com.moim.presentation.screen.home.model.RoomFilterStatus
import com.moim.presentation.util.WhileUiSubscribed
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<HomeUiState, HomeEvent>(HomeUiState()) {

    override fun checkLoading() = uiState.value.isLoading
    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    val filteredRooms = uiState.map { state ->
        val now = LocalDateTime.now()

        state.rooms.filter { room ->
            val deadline = LocalDateTime.parse(room.deadline)

            if (state.roomFilterStatus == RoomFilterStatus.ONGOING) {
                deadline.isAfter(now)
            } else {
                !deadline.isAfter(now)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileUiSubscribed,
        initialValue = emptyList()
    )

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
                        selectedDateTime = null,
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
                        selectedDateTime = null,
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