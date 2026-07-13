package com.moim.presentation.screen.roomdetail.model

data class RoomDetailUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val roomDetail: RoomDetailUiModel = RoomDetailUiModel()
)