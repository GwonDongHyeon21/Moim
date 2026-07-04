package com.moim.domain.repository

import com.moim.domain.model.UserInfo

interface UserRepository {

    suspend fun loginWithGoogle(idToken: String): Result<UserInfo>

    fun getAccessToken(): Flow<String?>
}