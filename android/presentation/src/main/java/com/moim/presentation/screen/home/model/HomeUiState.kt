package com.moim.presentation.screen.home.model

import com.moim.domain.model.RoomInfo

data class HomeUiState(
    val isLoading: Boolean = true,
    val rooms: List<RoomInfo> = emptyList()
)