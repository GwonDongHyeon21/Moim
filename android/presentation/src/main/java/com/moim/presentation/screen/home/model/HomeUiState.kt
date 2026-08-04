package com.moim.presentation.screen.home.model

import java.time.LocalDateTime

data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val title: String = "",
    val description: String = "",
    val selectedDateTime: LocalDateTime? = null,
    val roomFilterStatus: RoomFilterStatus = RoomFilterStatus.ONGOING,
    val isExpanded: Boolean = false,
    val roomOption: String = ""
)