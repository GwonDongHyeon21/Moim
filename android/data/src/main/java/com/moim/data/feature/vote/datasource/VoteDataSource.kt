package com.moim.data.feature.vote.datasource

import com.moim.data.feature.vote.model.CandidateResponse
import com.moim.data.feature.vote.model.VoteResultResponse

interface VoteDataSource {

    suspend fun createCandidate(roomId: Long, category: String, content: String): Result<Boolean>

    suspend fun getCandidates(roomId: Long, category: String): Result<List<CandidateResponse>>

    suspend fun castVote(candidateId: Long): Result<Boolean>

    suspend fun resetVotes(roomId: Long, category: String): Result<Boolean>

    suspend fun getVoteResults(roomId: Long): Result<List<VoteResultResponse>>
}