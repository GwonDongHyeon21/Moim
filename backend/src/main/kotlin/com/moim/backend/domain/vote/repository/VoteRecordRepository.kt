package com.moim.backend.domain.vote.repository

import com.moim.backend.domain.vote.entity.VoteRecord
import io.lettuce.core.dynamic.annotation.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VoteRecordRepository : JpaRepository<VoteRecord, Long> {

    fun findByUserIdAndCandidateId(userId: Long, candidateId: Long): VoteRecord?

    fun deleteByUserIdAndCandidateIdIn(userId: Long, candidateIds: List<Long>)

    @Query(
        """
        SELECT vr FROM VoteRecord vr 
        JOIN vr.candidate c
        WHERE vr.user.id = :userId 
        AND c.room.id = :roomId
    """
    )
    fun findAllByUserIdAndRoomId(
        @Param("userId") userId: Long,
        @Param("roomId") roomId: Long
    ): List<VoteRecord>

    fun countByCandidateId(candidateId: Long): Int
}