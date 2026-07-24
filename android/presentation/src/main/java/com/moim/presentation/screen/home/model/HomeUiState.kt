package com.moim.presentation.screen.home.model

import com.moim.presentation.model.RoomInfoUiModel
import java.time.LocalDateTime

data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val title: String = "",
    val description: String = "",
    val selectedDateTime: LocalDateTime? = null,
    val roomCode: String = "",
    val isExpanded: Boolean = false,
    val roomOption: String = "",
    val rooms: List<RoomInfoUiModel> = emptyList()
)