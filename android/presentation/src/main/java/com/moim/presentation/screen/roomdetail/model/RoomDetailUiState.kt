package com.moim.presentation.screen.roomdetail.model

import java.time.LocalDateTime

data class RoomDetailUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val roomDetail: RoomDetailUiModel = RoomDetailUiModel(),
    val title: String = "",
    val description: String = "",
    val maxCount: Int = 100,
    val selectedDateTime: LocalDateTime = LocalDateTime.now(),
    val voteResult: List<VoteResultUiModel> = emptyList()
)