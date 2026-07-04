package com.moim.backend.domain.user.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.moim.backend.core.error.ErrorException
import com.moim.backend.core.jwt.JwtProvider
import com.moim.backend.domain.user.dto.LoginResponse
import com.moim.backend.domain.user.dto.UserResponse
import com.moim.backend.domain.user.entity.User
import com.moim.backend.domain.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.util.*

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val redisTemplate: StringRedisTemplate,
    @Value($$"${oauth2.google.client-id}") private val googleClientId: String,
    @Value($$"${jwt.refresh-expiration}") private val refreshExpiration: Long
) {

    @Transactional
    fun googleLogin(idToken: String): LoginResponse {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList(googleClientId))
            .build()

        val idToken = verifier.verify(idToken) ?: throw ErrorException(
            httpStatus = HttpStatus.UNAUTHORIZED,
            errorCode = "INVALID_GOOGLE_TOKEN",
            message = "유효하지 않거나 위조된 구글 토큰입니다."
        )

        val payLoad = idToken.payload
        val googleSub = payLoad.subject
        val email = payLoad.email
        val name = payLoad["name"] as String? ?: "모임러"

        val user = userRepository.findByGoogleSub(googleSub) ?: userRepository.save(
            User(
                googleSub = googleSub,
                email = email,
                nickname = name
            )
        )
        val userId = user.id ?: throw ErrorException(
            httpStatus = HttpStatus.NOT_FOUND,
            errorCode = "USER_NOT_FOUND",
            message = "유저 저장/조회에 실패했습니다."
        )

        val accessToken = jwtProvider.createAccessToken(userId, user.email)
        val refreshToken = jwtProvider.createRefreshToken(userId)

        saveRefreshToken(userId, refreshToken)

        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = UserResponse.from(user)
        )
    }

    @Transactional
    fun reissueToken(refreshToken: String): LoginResponse {
        if (!jwtProvider.validateToken(refreshToken)) throw ErrorException(
            httpStatus = HttpStatus.UNAUTHORIZED,
            errorCode = "INVALID_TOKEN",
            message = "만료되거나 유효하지 않은 Refresh Token입니다. 다시 로그인해주세요."
        )

        val userIdString = redisTemplate.opsForValue().get("RT:$refreshToken")
            ?: throw ErrorException(
                httpStatus = HttpStatus.UNAUTHORIZED,
                errorCode = "TOKEN_EXPIRED",
                message = "로그아웃 되었거나 서버에서 만료된 토큰입니다."
            )
        val userId = userIdString.toLong()

        val user = userRepository.findById(userId).orElseThrow {
            ErrorException(
                httpStatus = HttpStatus.NOT_FOUND,
                errorCode = "USER_NOT_FOUND",
                message = "존재하지 않는 유저입니다."
            )
        }

        val newAccessToken = jwtProvider.createAccessToken(userId, user.email)
        val newRefreshToken = jwtProvider.createRefreshToken(userId)

        redisTemplate.delete("RT:$refreshToken")
        saveRefreshToken(userId, newRefreshToken)

        return LoginResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            user = UserResponse.from(user)
        )
    }

    fun saveRefreshToken(userId: Long, refreshToken: String) {
        redisTemplate.opsForValue().set(
            "RT:$refreshToken",
            userId.toString(),
            Duration.ofMillis(refreshExpiration)
        )
    }

    fun removeRefreshToken(refreshToken: String): Boolean? {
        return redisTemplate.delete("RT:$refreshToken")
    }
}