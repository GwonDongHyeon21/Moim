package com.moim.backend.domain.room.dto

data class CategoryVoteStatusDto(
    val category: String,
    val isVoted: Boolean
)