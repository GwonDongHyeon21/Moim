package com.moim.backend.domain.room.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "rooms")
class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "room_code", nullable = false, unique = true, length = 20)
    val code: String,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column(length = 500)
    var description: String? = null,

    @Column(name = "max_count", nullable = false)
    var maxCount: Int,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)