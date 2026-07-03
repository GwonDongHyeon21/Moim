package com.moim.data.feature.user.datasource

import com.moim.data.common.model.ApiResponse
import com.moim.data.feature.user.model.GoogleLoginRequest
import com.moim.data.feature.user.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/v1/users/login/google")
    suspend fun googleLogin(
        @Body request: GoogleLoginRequest
    ): ApiResponse<LoginResponse>
}