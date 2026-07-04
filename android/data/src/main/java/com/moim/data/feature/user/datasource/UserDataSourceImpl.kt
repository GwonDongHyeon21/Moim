package com.moim.data.feature.user.datasource

import com.moim.data.common.model.ApiResponse
import com.moim.data.feature.user.model.GoogleLoginRequest
import com.moim.data.feature.user.model.LoginResponse
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {

    override suspend fun loginWithGoogle(idToken: String): ApiResponse<LoginResponse> {
        return userService.loginWithGoogle(GoogleLoginRequest(idToken))
    }
}