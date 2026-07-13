package com.moim.presentation.screen.roomdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moim.domain.repository.RoomRepository
import com.moim.presentation.screen.roomdetail.model.RoomDetailAction
import com.moim.presentation.screen.roomdetail.model.RoomDetailEvent
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiState
import com.moim.presentation.screen.roomdetail.model.toUiModel
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
class RoomDetailViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoomDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<RoomDetailEvent>(BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: RoomDetailAction) {
        when (action) {
            is RoomDetailAction.LoadRoomDetail -> loadRoomDetail(action.roomId)
            RoomDetailAction.NavigateBack -> _uiEvent.trySend(RoomDetailEvent.NavigateBack)
        }
    }

    private fun loadRoomDetail(roomId: Long) {
        viewModelScope.launch {
            roomRepository.loadRoomDetail(roomId.toString())
                .onSuccess { data ->
                    _uiState.update { it.copy(roomDetail = data.toUiModel()) }
                }.onFailure {
                    // 아직
                }
        }
    }
}