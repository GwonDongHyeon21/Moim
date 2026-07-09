package com.moim.presentation.screen.roomdetail.model

data class RoomDetailUiState(
    val isLoading: Boolean = true,
    val roomDetail: RoomDetailUiModel = RoomDetailUiModel()
)