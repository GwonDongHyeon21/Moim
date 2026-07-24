package com.moim.presentation.screen.roomdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moim.domain.repository.RoomRepository
import com.moim.presentation.navigation.RoomDetail
import com.moim.presentation.screen.roomdetail.model.RoomDetailAction
import com.moim.presentation.screen.roomdetail.model.RoomDetailEvent
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiState
import com.moim.presentation.screen.roomdetail.model.toUiModel
import com.moim.presentation.util.WhileUiSubscribed
import com.moim.presentation.util.snackbar.SnackBarEvent
import com.moim.presentation.util.snackbar.SnackBarManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber

@HiltViewModel(assistedFactory = RoomDetailViewModel.Factory::class)
class RoomDetailViewModel @AssistedInject constructor(
    @Assisted route: RoomDetail,
    private val roomRepository: RoomRepository,
    private val snackBarManager: SnackBarManager
) : ViewModel() {

    private val refreshTrigger = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<RoomDetailUiState> = refreshTrigger
        .flatMapLatest { triggerCount ->
            flow {
                val isRefreshing = triggerCount != 0
                if (isRefreshing) {
                    emit(RoomDetailUiState(isLoading = false, isRefreshing = true))
                } else {
                    emit(RoomDetailUiState(isLoading = true, isRefreshing = false))
                }

                roomRepository.loadRoomDetail(route.roomId.toString())
                    .onSuccess { data ->
                        emit(
                            RoomDetailUiState(
                                isLoading = false,
                                isRefreshing = false,
                                roomDetail = data.toUiModel()
                            )
                        )
                    }
                    .onFailure { exception ->
                        _uiEvent.trySend(RoomDetailEvent.NavigateBack)
                        snackBarManager.show(SnackBarEvent.DATA_LOAD_FAILED)

                        Timber.e(exception)
                    }
            }
        }.stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = RoomDetailUiState(isLoading = true)
        )

    private val _uiEvent = Channel<RoomDetailEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: RoomDetailAction) {
        when (action) {
            is RoomDetailAction.RefreshRoomDetail -> refreshTrigger.update { it + 1 }
            RoomDetailAction.NavigateBack -> _uiEvent.trySend(RoomDetailEvent.NavigateBack)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(route: RoomDetail): RoomDetailViewModel
    }
}