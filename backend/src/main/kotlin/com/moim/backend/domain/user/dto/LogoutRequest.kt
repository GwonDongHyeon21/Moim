package com.moim.backend.domain.user.dto

data class LogoutRequest(
    val refreshToken: String
)