package com.moim.presentation.screen.home.model

import com.moim.presentation.model.RoomInfoUiModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val rooms: List<RoomInfoUiModel> = emptyList()
)