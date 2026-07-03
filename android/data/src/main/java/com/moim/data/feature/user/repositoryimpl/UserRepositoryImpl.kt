package com.moim.data.feature.user.repositoryimpl

import com.moim.data.feature.user.datasource.UserService
import com.moim.data.feature.user.model.GoogleLoginRequest
import com.moim.domain.model.UserInfo
import com.moim.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserService
) : UserRepository {

    override suspend fun loginWithGoogle(idToken: String): Result<UserInfo> {
        return runCatching {
            val response = userApiService.googleLogin(GoogleLoginRequest(idToken))

            if (response.success && response.data != null) {
                val loginData = response.data

                // 안드로이드 기기(EncryptedSharedPreferences 등)에 accessToken, refreshToken을 저장하는 로직

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