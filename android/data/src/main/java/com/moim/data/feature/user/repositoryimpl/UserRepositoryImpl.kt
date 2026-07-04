package com.moim.data.feature.user.repositoryimpl

import com.moim.data.common.source.TokenDataStore
import com.moim.data.feature.user.datasource.UserDataSource
import com.moim.domain.model.UserInfo
import com.moim.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val tokenDataStore: TokenDataStore
) : UserRepository {

    override suspend fun loginWithGoogle(idToken: String): Result<UserInfo> {
        return runCatching {
            val response = userDataSource.loginWithGoogle(idToken)

            if (response.success && response.data != null) {
                val loginData = response.data

                tokenDataStore.saveTokens(
                    accessToken = loginData.accessToken,
                    refreshToken = loginData.refreshToken
                )

                UserInfo(
                    id = loginData.user.id,
                    email = loginData.user.email,
                    nickname = loginData.user.nickname,
                    profileImageUrl = loginData.user.profileImageUrl
                )
            } else {
                throw Exception(response.error?.message ?: "서버 통신 실패")
            }
        }
    }
}