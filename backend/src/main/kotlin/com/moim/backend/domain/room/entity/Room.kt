package com.moim.backend.domain.room.entity

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "rooms")
class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "room_code", nullable = false, unique = true, length = 8)
    val code: String,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column(length = 500)
    var description: String? = null,

    @Column(name = "max_count", nullable = false)
    var maxCount: Int,

    @Column(nullable = false)
    var deadline: LocalDateTime,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
) {
    fun update(
        title: String,
        description: String?,
        maxCount: Int,
        deadline: LocalDateTime
    ) {
        this.title = title
        this.description = description
        this.maxCount = maxCount
        this.deadline = deadline
    }

    fun delete() {
        this.isDeleted = true
    }
}