package com.moim.domain.feature.vote.repository

import com.moim.domain.feature.vote.model.CandidateInfo
import com.moim.domain.feature.vote.model.CategoryInfo
import com.moim.domain.feature.vote.model.UpdateCandidateParams
import com.moim.domain.feature.vote.model.VoteResultInfo

interface VoteRepository {

    suspend fun getCategories(): Result<List<CategoryInfo>>

    suspend fun createCandidate(roomId: Long, category: String, content: String): Result<Boolean>

    suspend fun updateCandidates(
        roomId: Long,
        candidates: List<UpdateCandidateParams>
    ): Result<Boolean>

    suspend fun getCandidates(roomId: Long, category: String): Result<List<CandidateInfo>>

    suspend fun castVote(candidateId: Long): Result<Boolean>

    suspend fun resetVotes(roomId: Long, category: String): Result<Boolean>

    suspend fun getMyCandidates(roomId: Long, category: String): Result<List<CandidateInfo>>

    suspend fun getVoteResults(roomId: Long): Result<List<VoteResultInfo>>
}