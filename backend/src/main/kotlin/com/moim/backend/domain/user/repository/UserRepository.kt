package com.moim.backend.domain.user.repository

import com.moim.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByGoogleSub(googleSub: String): User?

    fun existsByNickname(nickname: String): Boolean
}