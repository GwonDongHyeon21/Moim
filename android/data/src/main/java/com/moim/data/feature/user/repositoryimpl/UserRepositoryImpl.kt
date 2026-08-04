package com.moim.data.feature.user.repositoryimpl

import com.moim.data.common.source.TokenDataStore
import com.moim.data.feature.user.datasource.UserDataSource
import com.moim.data.feature.user.model.toDomain
import com.moim.data.common.model.ErrorType
import com.moim.domain.feature.user.model.UserInfo
import com.moim.domain.feature.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val tokenDataStore: TokenDataStore
) : UserRepository {

    override suspend fun loginWithGoogle(idToken: String): Result<UserInfo> {
        return userDataSource.loginWithGoogle(idToken)
            .onSuccess { response ->
                tokenDataStore.saveTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            }.map { data ->
                data.user.toDomain()
            }
    }

    override suspend fun logout(): Result<Boolean> {
        val refreshToken = tokenDataStore.refreshTokenFlow.firstOrNull()

        if (refreshToken.isNullOrBlank()) {
            return Result.failure(ErrorType.TokenNotFound())
        }

        tokenDataStore.clearTokens()

        return userDataSource.logout(refreshToken)
    }

    override suspend fun reissueToken(): Result<Boolean> {
        val refreshToken = tokenDataStore.refreshTokenFlow.firstOrNull()

        if (refreshToken.isNullOrBlank()) {
            return Result.failure(ErrorType.TokenNotFound())
        }

        return userDataSource.reissueTokens(refreshToken)
            .onSuccess { response ->
                tokenDataStore.saveTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            }.map { data ->
                data.accessToken.isNotEmpty() && data.refreshToken.isNotEmpty()
            }
    }

    override fun getAccessToken(): Flow<String?> {
        return tokenDataStore.accessTokenFlow
    }
}