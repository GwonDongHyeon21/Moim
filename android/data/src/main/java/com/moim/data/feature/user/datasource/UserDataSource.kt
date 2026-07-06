package com.moim.data.feature.user.datasource

import com.moim.data.feature.user.model.LoginResponse
import com.moim.data.feature.user.model.TokenResponse

interface UserDataSource {

    suspend fun loginWithGoogle(idToken: String): Result<LoginResponse>

    suspend fun logout(refreshToken: String): Result<Boolean>

    suspend fun reissueTokens(refreshToken: String): Result<TokenResponse>
}