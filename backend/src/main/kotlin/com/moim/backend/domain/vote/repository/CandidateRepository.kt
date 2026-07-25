package com.moim.backend.domain.vote.repository

import com.moim.backend.domain.vote.entity.Candidate
import com.moim.backend.domain.vote.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CandidateRepository : JpaRepository<Candidate, Long> {

    fun findAllByRoomIdAndCategory(roomId: Long, category: Category): List<Candidate>

    fun findAllByRoomId(roomId: Long): List<Candidate>
}
