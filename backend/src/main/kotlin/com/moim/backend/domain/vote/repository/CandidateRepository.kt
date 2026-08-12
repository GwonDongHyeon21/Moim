package com.moim.backend.domain.vote.repository

import com.moim.backend.core.model.Category
import com.moim.backend.domain.vote.entity.Candidate
import org.springframework.data.jpa.repository.JpaRepository

interface CandidateRepository : JpaRepository<Candidate, Long> {

    fun findAllByRoomIdAndCategory(roomId: Long, category: Category): List<Candidate>

    fun findAllByRoomId(roomId: Long): List<Candidate>

    fun countByRoomIdAndCategoryAndUserId(roomId: Long, category: Category, userId: Long): Int

    fun findAllByRoomIdAndCategoryAndUserId(roomId: Long, category: Category, userId: Long): List<Candidate>
}
