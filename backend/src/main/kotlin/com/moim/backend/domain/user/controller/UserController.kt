package com.moim.backend.domain.user.controller

import com.moim.backend.core.error.ErrorException
import com.moim.backend.core.response.ApiResponse
import com.moim.backend.core.util.JwtProvider
import com.moim.backend.domain.user.dto.GoogleLoginRequest
import com.moim.backend.domain.user.dto.LoginResponse
import com.moim.backend.domain.user.dto.UserResponse
import com.moim.backend.domain.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val jwtProvider: JwtProvider
) {

    @PostMapping("/login/google")
    fun googleLogin(
        @RequestBody request: GoogleLoginRequest
    ): ApiResponse<LoginResponse> {

        val user = userService.googleLogin(request.idToken)

        val userId = user.id ?: throw ErrorException("User not found", "유저 ID가 존재하지 않습니다.")

        val accessToken = jwtProvider.createAccessToken(userId = userId, email = user.email)
        val refreshToken = jwtProvider.createRefreshToken(userId)

        val response = LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = UserResponse.from(user)
        )

        return ApiResponse.success(response)
    }
}