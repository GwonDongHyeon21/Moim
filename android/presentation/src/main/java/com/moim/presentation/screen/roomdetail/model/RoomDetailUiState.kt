package com.moim.presentation.screen.roomdetail.model

data class RoomDetailUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val roomDetail: RoomDetailUiModel = RoomDetailUiModel(),
    val voteResult: List<VoteResultUiModel> = emptyList()
)