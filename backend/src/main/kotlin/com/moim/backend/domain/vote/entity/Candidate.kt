package com.moim.backend.domain.vote.entity

import com.moim.backend.domain.room.entity.Room
import com.moim.backend.domain.user.entity.User
import com.moim.backend.domain.vote.model.Category
import jakarta.persistence.*

@Entity
@Table(name = "vote_candidates")
class Candidate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    val room: Room,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val category: Category,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false, length = 100)
    var content: String
)