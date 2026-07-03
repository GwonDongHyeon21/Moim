package com.moim.backend.domain.user.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.moim.backend.core.error.ErrorException
import com.moim.backend.domain.user.entity.User
import com.moim.backend.domain.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    @Value($$"${oauth2.google.client-id}") private val googleClientId: String
) {

    @Transactional
    fun googleLogin(idToken: String): User {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList(googleClientId))
            .build()

        val idToken = verifier.verify(idToken) ?: throw ErrorException("INVALID_GOOGLE_TOKEN", "유효하지 않거나 위조된 구글 토큰입니다.")

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

        return user
    }
}