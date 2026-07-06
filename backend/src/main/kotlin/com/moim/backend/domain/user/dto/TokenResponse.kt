package com.moim.backend.domain.user.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
