package com.moim.domain.repository

import com.moim.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun loginWithGoogle(idToken: String): Result<UserInfo>

    suspend fun logout(): Result<Boolean>

    suspend fun reissueToken(): Result<Boolean>

    fun getAccessToken(): Flow<String?>
}