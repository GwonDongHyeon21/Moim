package com.moim.backend.domain.user.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.moim.backend.core.error.ErrorException
import com.moim.backend.core.jwt.JwtProvider
import com.moim.backend.domain.user.dto.LoginResponse
import com.moim.backend.domain.user.dto.TokenResponse
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
        val sessionId = UUID.randomUUID().toString()

        val accessToken = jwtProvider.createAccessToken(userId, email)
        val refreshToken = jwtProvider.createRefreshToken(userId, sessionId)

        saveRefreshToken(userId, sessionId, refreshToken)

        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            user = UserResponse.from(user)
        )
    }

    @Transactional
    fun reissueToken(refreshToken: String): TokenResponse {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw ErrorException(
                httpStatus = HttpStatus.UNAUTHORIZED,
                errorCode = "INVALID_TOKEN",
                message = "만료되거나 유효하지 않은 Refresh Token입니다. 다시 로그인해주세요."
            )
        }

        val userId = jwtProvider.getUserIdFromToken(refreshToken)
        val sessionId = jwtProvider.getSessionIdFromToken(refreshToken)

        validateTokenExpiration(userId, sessionId, refreshToken)

        val user = userRepository.findById(userId).orElseThrow {
            ErrorException(
                httpStatus = HttpStatus.NOT_FOUND,
                errorCode = "USER_NOT_FOUND",
                message = "존재하지 않는 유저입니다."
            )
        }

        val newAccessToken = jwtProvider.createAccessToken(userId, user.email)
        val newRefreshToken = jwtProvider.createRefreshToken(userId, sessionId)

        saveRefreshToken(userId, sessionId, newRefreshToken)

        return TokenResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    private fun validateTokenExpiration(userId: Long, sessionId: String, refreshToken: String) {
        val previousToken = redisTemplate.opsForValue().get(redisKey(userId, sessionId)) ?: throw ErrorException(
            httpStatus = HttpStatus.UNAUTHORIZED,
            errorCode = "TOKEN_EXPIRED",
            message = "이미 로그아웃 되었거나 만료된 세션입니다. 다시 로그인해주세요."
        )

        if (previousToken != refreshToken) {
            val allSessionKeys = redisTemplate.keys(redisKeyAll(userId))
            if (!allSessionKeys.isNullOrEmpty()) {
                redisTemplate.delete(allSessionKeys)
            }

            throw ErrorException(
                httpStatus = HttpStatus.UNAUTHORIZED,
                errorCode = "SECURITY_BREACH",
                message = "비정상적인 접근이 감지되어 보안을 위해 강제 로그아웃 처리되었습니다."
            )
        }
    }

    private fun saveRefreshToken(userId: Long, sessionId: String, refreshToken: String) {
        redisTemplate.opsForValue().set(
            redisKey(userId, sessionId),
            refreshToken,
            Duration.ofMillis(refreshExpiration)
        )
    }

    fun logout(refreshToken: String): Boolean {
        return try {
            val userId = jwtProvider.getUserIdFromToken(refreshToken)
            val sessionId = jwtProvider.getSessionIdFromToken(refreshToken)

            redisTemplate.delete(redisKey(userId, sessionId))
        } catch (_: Exception) {
            false
        }
    }

    private fun redisKey(userId: Long, sessionId: String) = "RT:$userId:$sessionId"
    private fun redisKeyAll(userId: Long) = "RT:$userId:*"
}