package com.moim.presentation.screen.roomdetail

import com.moim.domain.feature.room.repository.RoomRepository
import com.moim.domain.feature.vote.repository.VoteRepository
import com.moim.presentation.base.BaseViewModel
import com.moim.presentation.navigation.RoomDetail
import com.moim.presentation.screen.roomdetail.model.RoomDetailAction
import com.moim.presentation.screen.roomdetail.model.RoomDetailEvent
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiState
import com.moim.presentation.screen.roomdetail.model.toUiModel
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.time.LocalDateTime

@HiltViewModel(assistedFactory = RoomDetailViewModel.Factory::class)
class RoomDetailViewModel @AssistedInject constructor(
    @Assisted route: RoomDetail,
    private val roomRepository: RoomRepository,
    private val voteRepository: VoteRepository,
    private val snackBarManager: SnackBarManager
) : BaseViewModel<RoomDetailUiState, RoomDetailEvent>(RoomDetailUiState()) {

    override fun checkLoading() = uiState.value.isLoading
    override fun updateLoading(isLoading: Boolean) = updateState { copy(isLoading = isLoading) }

    private val roomId = route.roomId

    init {
        doAction { loadRoomDetail() }
    }

    fun onAction(action: RoomDetailAction) {
        when (action) {
            is RoomDetailAction.RefreshRoomDetail -> refreshRoomDetail()

            is RoomDetailAction.NavigateToVote ->
                sendEvent(RoomDetailEvent.NavigateToVote(roomId, action.category))

            is RoomDetailAction.NavigateToCandidateCreate ->
                sendEvent(RoomDetailEvent.NavigateToCandidateCreate(roomId))

            RoomDetailAction.NavigateBack -> sendEvent(RoomDetailEvent.NavigateBack)
        }
    }

    private suspend fun loadRoomDetail() {
        roomRepository.loadRoomDetail(roomId)
            .onSuccess { data ->
                updateState { copy(roomDetail = data.toUiModel()) }

                if (LocalDateTime.parse(data.roomInfo.deadline) < LocalDateTime.now()) {
                    loadVoteResults()
                }
            }
            .onFailure { exception ->
                sendEvent(RoomDetailEvent.NavigateBack)
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                Timber.e(exception)
            }
    }

    private fun refreshRoomDetail() = doAction(
        customCheck = { uiState.value.isRefreshing },
        customUpdate = { updateState { copy(isRefreshing = it) } }
    ) {
        loadRoomDetail()
    }

    private suspend fun loadVoteResults() {
        voteRepository.getVoteResults(roomId)
            .onSuccess { data ->
                updateState { copy(voteResult = data.map { it.toUiModel() }) }
            }.onFailure { exception ->
                sendEvent(RoomDetailEvent.NavigateBack)
                snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                Timber.e(exception)
            }
    }

    @AssistedFactory
    interface Factory {
        fun create(route: RoomDetail): RoomDetailViewModel
    }
}