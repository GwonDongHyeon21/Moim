package com.moim.backend.domain.user.controller

import com.moim.backend.core.response.ApiResponse
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
//    private val jwtProvider: JwtProvider
) {

    @PostMapping("/login/google")
    fun googleLogin(
        @RequestBody request: GoogleLoginRequest
    ): ApiResponse<LoginResponse> {

        val user = userService.googleLogin(request.idToken)

        val accessToken = ""    // jwt accessToken 생성
        val refreshToken = ""   // jwt refreshToken 생성

        val response = LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = UserResponse.from(user)
        )

        return ApiResponse.success(response)
    }
}