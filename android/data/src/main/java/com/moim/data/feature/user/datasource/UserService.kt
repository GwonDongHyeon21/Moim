package com.moim.data.feature.user.datasource

import com.moim.data.common.model.ApiResponse
import com.moim.data.feature.user.model.GoogleLoginRequest
import com.moim.data.feature.user.model.LoginResponse
import com.moim.data.feature.user.model.LogoutRequest
import com.moim.data.feature.user.model.ReissueRequest
import com.moim.data.feature.user.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/v1/users/login/google")
    suspend fun loginWithGoogle(
        @Body request: GoogleLoginRequest
    ): ApiResponse<LoginResponse>

    @POST("api/v1/users/logout")
    suspend fun logout(
        @Body request: LogoutRequest
    ): ApiResponse<Boolean>

    @POST("api/v1/users/reissue")
    suspend fun reissueTokens(
        @Body request: ReissueRequest
    ): ApiResponse<TokenResponse>
}