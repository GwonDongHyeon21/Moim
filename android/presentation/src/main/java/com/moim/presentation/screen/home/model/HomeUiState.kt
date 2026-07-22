package com.moim.presentation.screen.home.model

import com.moim.presentation.model.RoomInfoUiModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val title: String = "",
    val description: String = "",
    val roomCode: String = "",
    val isExpanded: Boolean = false,
    val roomOption: String = "",
    val rooms: List<RoomInfoUiModel> = emptyList()
)