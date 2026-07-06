package com.moim.data.feature.user.datasource

import com.moim.data.common.network.apiCall
import com.moim.data.feature.user.model.GoogleLoginRequest
import com.moim.data.feature.user.model.LoginResponse
import com.moim.data.feature.user.model.LogoutRequest
import com.moim.data.feature.user.model.ReissueRequest
import com.moim.data.feature.user.model.TokenResponse
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {

    override suspend fun loginWithGoogle(idToken: String): Result<LoginResponse> {
        return apiCall { userService.loginWithGoogle(GoogleLoginRequest(idToken)) }
    }

    override suspend fun logout(refreshToken: String): Result<Boolean> {
        return apiCall { userService.logout(LogoutRequest(refreshToken)) }
    }

    override suspend fun reissueTokens(refreshToken: String): Result<TokenResponse> {
        return apiCall { userService.reissueTokens(ReissueRequest(refreshToken)) }
    }
}