package com.moim.backend.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "google_sub", nullable = false, unique = true, length = 100)
    val googleSub: String,

    @Column(nullable = false, unique = true, length = 50)
    val email: String,

    @Column(nullable = false, length = 20)
    var nickname: String,

    @Column(name = "profile_image_url", nullable = true)
    var profileImageUrl: String? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateNickname(newNickname: String) {
        require(newNickname.isNotBlank()) { "닉네임은 공백일 수 없습니다." }
        this.nickname = newNickname
    }

    fun updateProfileImage(url: String?) {
        this.profileImageUrl = url
    }
}