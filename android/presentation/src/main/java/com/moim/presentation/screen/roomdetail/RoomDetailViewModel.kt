package com.moim.presentation.screen.roomdetail

import com.moim.domain.repository.RoomRepository
import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.screen.roomdetail.model.RoomDetailAction
import com.moim.presentation.screen.roomdetail.model.RoomDetailEvent
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiState
import com.moim.presentation.screen.roomdetail.model.toUiModel
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RoomDetailViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<RoomDetailUiState, RoomDetailEvent>(RoomDetailUiState()) {

    override fun checkLoading() = uiState.value.isLoading
    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    fun onAction(action: RoomDetailAction) {
        when (action) {
            is RoomDetailAction.LoadRoomDetail -> loadRoomDetail(action.roomId)
            is RoomDetailAction.RefreshRoomDetail -> refreshRoomDetail(action.roomId)
            RoomDetailAction.NavigateBack -> sendEvent(RoomDetailEvent.NavigateBack)
        }
    }

    private fun loadRoomDetail(roomId: Long) = doAction {
        roomRepository.loadRoomDetail(roomId.toString())
            .onSuccess { data ->
                updateState { copy(roomDetail = data.toUiModel()) }
            }.onFailure { exception ->
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                Timber.e(exception)
            }
    }

    private fun refreshRoomDetail(roomId: Long) = doAction(
        customCheck = { uiState.value.isRefreshing },
        customUpdate = { updateState { copy(isRefreshing = it) } }
    ) {
        loadRoomDetail(roomId)
    }
}