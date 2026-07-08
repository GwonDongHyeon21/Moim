package com.moim.backend.domain.user.dto

import com.moim.backend.core.error.ErrorCode
import com.moim.backend.core.error.ErrorException
import com.moim.backend.domain.user.entity.User
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(user: User): UserResponse = UserResponse(
            id = user.id ?: throw ErrorException(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                errorCode = ErrorCode.USER_NOT_FOUND
            ),
            email = user.email,
            nickname = user.nickname,
            profileImageUrl = user.profileImageUrl,
            createdAt = user.createdAt
        )
    }
}