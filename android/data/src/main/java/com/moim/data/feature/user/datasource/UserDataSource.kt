package com.moim.data.feature.user.datasource

import com.moim.data.common.model.ApiResponse
import com.moim.data.feature.user.model.LoginResponse

interface UserDataSource {

    suspend fun loginWithGoogle(idToken: String): ApiResponse<LoginResponse>
}