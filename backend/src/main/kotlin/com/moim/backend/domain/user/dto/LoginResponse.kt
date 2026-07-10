package com.moim.backend.domain.user.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponse
)