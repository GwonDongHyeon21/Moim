package com.moim.domain.feature.user.repository

import com.moim.domain.feature.user.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun loginWithGoogle(idToken: String): Result<UserInfo>

    suspend fun logout(): Result<Boolean>

    suspend fun reissueToken(): Result<Boolean>

    fun getAccessToken(): Flow<String?>
}