package com.moim.presentation.screen.roomdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moim.domain.repository.RoomRepository
import com.moim.presentation.screen.roomdetail.model.toUiModel
import com.moim.presentation.navigation.RoomDetail
import com.moim.presentation.screen.roomdetail.model.RoomDetailEvent
import com.moim.presentation.screen.roomdetail.model.RoomDetailUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = RoomDetailViewModel.Factory::class)
class RoomDetailViewModel @AssistedInject constructor(
    @Assisted route: RoomDetail,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val roomId = route.roomId

    private val _uiState = MutableStateFlow(RoomDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<RoomDetailEvent>(BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadRoomDetail(roomId)
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


    @AssistedFactory
    interface Factory {
        fun create(route: RoomDetail): RoomDetailViewModel
    }
}