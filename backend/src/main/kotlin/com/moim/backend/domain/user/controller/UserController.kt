package com.moim.backend.domain.user.controller

import com.moim.backend.core.response.ApiResponse
import com.moim.backend.domain.user.dto.GoogleLoginRequest
import com.moim.backend.domain.user.dto.LoginResponse
import com.moim.backend.domain.user.dto.LogoutRequest
import com.moim.backend.domain.user.dto.ReissueRequest
import com.moim.backend.domain.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/login/google")
    fun googleLogin(
        @RequestBody request: GoogleLoginRequest
    ): ApiResponse<LoginResponse> {
        val response = userService.googleLogin(request.idToken)

        return ApiResponse.success(response)
    }

    @PostMapping("/logout")
    fun logout(
        @RequestBody request: LogoutRequest
    ): ApiResponse<Boolean> {
        val response = userService.removeRefreshToken(request.refreshToken)

        return ApiResponse.success(response)
    }

    @PostMapping("/reissue")
    fun reissue(
        @RequestBody request: ReissueRequest
    ): ApiResponse<LoginResponse> {
        val response = userService.reissueToken(request.refreshToken)

        return ApiResponse.success(response)
    }
}