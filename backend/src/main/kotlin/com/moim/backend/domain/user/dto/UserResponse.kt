package com.moim.backend.domain.user.dto

import com.moim.backend.core.error.ErrorException
import com.moim.backend.domain.user.entity.User
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
                errorCode = "SERVER_INTERNAL_ERROR",
                message = "저장된 유저의 ID가 존재하지 않습니다. 엔티티 상태를 확인하세요."
            ),
            email = user.email,
            nickname = user.nickname,
            profileImageUrl = user.profileImageUrl,
            createdAt = user.createdAt
        )
    }
}