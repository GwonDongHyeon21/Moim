package com.moim.presentation.screen.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.moim.domain.feature.room.model.CreateUpdateRoomParams
import com.moim.domain.feature.room.repository.RoomRepository
import com.moim.domain.feature.user.repository.UserRepository
import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.model.RoomInfoUiModel
import com.moim.presentation.model.toUiModel
import com.moim.presentation.screen.home.model.HomeAction
import com.moim.presentation.screen.home.model.HomeEvent
import com.moim.presentation.screen.home.model.HomeUiState
import com.moim.presentation.screen.home.model.RoomFilterStatus
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    val ongoingRoomsPagingItems: Flow<PagingData<RoomInfoUiModel>> =
        roomRepository.getRoomsPaging(RoomFilterStatus.ONGOING.name)
            .map { pagingData -> pagingData.map { it.toUiModel() } }
            .cachedIn(viewModelScope)

    val closedRoomsPagingItems: Flow<PagingData<RoomInfoUiModel>> =
        roomRepository.getRoomsPaging(RoomFilterStatus.CLOSED.name)
            .map { pagingData -> pagingData.map { it.toUiModel() } }
            .cachedIn(viewModelScope)

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ClickRoom -> sendEvent(HomeEvent.NavigateToRoomDetail(action.roomId))

            is HomeAction.ClickDialog ->
                updateState { copy(isExpanded = action.isExpanded, roomOption = action.roomOption) }

            is HomeAction.OnTitleChanged -> updateState { copy(title = action.title) }

            is HomeAction.OnDescriptionChanged -> updateState { copy(description = action.description) }

            is HomeAction.OnDateTimeSelected -> updateState { copy(selectedDateTime = action.selectedDateTime) }

            is HomeAction.OnRoomFilterStatusSelected -> updateState { copy(roomFilterStatus = action.roomFilterStatus) }

            is HomeAction.CreateRoom -> createRoom(action.deadline)

            is HomeAction.JoinRoom -> joinRoom(action.roomCode)

            is HomeAction.OnRefreshing -> updateState { copy(isRefreshing = action.isRefreshing) }

            HomeAction.Logout -> logout()
        }
    }

    private fun createRoom(deadline: String) = doAction {
        updateState { copy(isExpanded = false, roomOption = "") }
        roomRepository.createRoom(
            CreateUpdateRoomParams(
                title = uiState.value.title,
                description = uiState.value.description,
                deadline = deadline
            )
        ).onSuccess { data ->
            sendEvent(HomeEvent.NavigateToRoomDetail(data.id))
            updateState {
                copy(
                    title = "",
                    description = "",
                    selectedDateTime = null
                )
            }
        }.onFailure { exception ->
            snackBarManager.show(SnackBarEvent.DATA_SAVE_FAILED)

            Timber.e(exception)
        }
    }

    private fun joinRoom(roomCode: String) = doAction {
        updateState { copy(isExpanded = false, roomOption = "") }
        roomRepository.joinRoom(roomCode)
            .onSuccess { data ->
                sendEvent(HomeEvent.NavigateToRoomDetail(data.id))
                updateState {
                    copy(
                        title = "",
                        description = "",
                        selectedDateTime = null
                    )
                }
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                Timber.e(exception)
            }
    }

    private fun logout() = doAction {
        userRepository.logout()
    }
}